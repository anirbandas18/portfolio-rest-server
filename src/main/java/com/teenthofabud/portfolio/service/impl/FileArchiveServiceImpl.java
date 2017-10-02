package com.teenthofabud.portfolio.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.dto.FileArchiveMetadata;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.service.FileArchiveService;

@Component
@Transactional(rollbackFor = {SdkClientException.class, AmazonServiceException.class, IOException.class})
public class FileArchiveServiceImpl implements FileArchiveService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileArchiveService.class);
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.s3.resume-bucket}")
	private String resumeBucket;
	@Value("${aws.s3.avatar-bucket}")
	private String avatarBucket;
	@Value(":")
	private String keySeparator;
	@Value("150")
	private int years;


	@Override
	public FileArchiveMetadata uploadFileToBucket(FreelancerFileDTO dto) throws IOException {
		// TODO Auto-generated method stub
		String bucketName = dto.getType() == FreelancerFile.AVATAR ? avatarBucket : resumeBucket;
		byte[] bytes = dto.getFile().getBytes();
		String fileMD5FromApp = DigestUtils.md5DigestAsHex(bytes);
		String key = dto.getId() + keySeparator + dto.getFile().getOriginalFilename() + String.valueOf(System.currentTimeMillis());
		String encodedKey = Base64Utils.encodeToUrlSafeString(key.getBytes());
		LOG.info("Uploading freelancer's file to AWS S3: {}", dto);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(dto.getFile().getContentType());
		metadata.setContentLength(dto.getFile().getSize());
		InputStream is = dto.getFile().getInputStream();
		try {
			PutObjectRequest request = new PutObjectRequest(bucketName, encodedKey, is, metadata);
			request = request.withCannedAcl(CannedAccessControlList.PublicRead);
			PutObjectResult response = s3Client.putObject(request);
			String fileMD5FromS3 = response.getContentMd5();
			Boolean status = fileMD5FromApp.equalsIgnoreCase(fileMD5FromS3);
			/*Date expiration = DateTime.now().plusYears(years).toDate();// a large expiration time
			GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, encodedKey);
			urlRequest.setMethod(HttpMethod.GET);
			urlRequest.setExpiration(expiration);
			URL externalUrl = s3Client.generatePresignedUrl(urlRequest);
			*/
			URL externalUrl = s3Client.getUrl(bucketName, encodedKey);
			FileArchiveMetadata archiveMetadata = new FileArchiveMetadata(encodedKey, externalUrl.toString());
			LOG.info("Freelancer file uploaded to AWS S3: {} with extrenal URL: {}", status, externalUrl);
			return archiveMetadata;
		} catch (SdkClientException e) {
			LOG.error("Error uploading object to AWS S3: {}", e.getMessage());
			throw new IOException(e);
		} finally {
			is.close();
		}
		
	}


	@Override
	public MultipartFile downloadFileFromBucket(String key, FreelancerFile type) throws IOException {
		// TODO Auto-generated method stub
		byte[] decodedBytes = Base64Utils.decodeFromString(key);
		String decodedKey = new String(decodedBytes);
		String fileName = decodedKey.split(keySeparator)[1];
		String bucketName = type == FreelancerFile.AVATAR ? avatarBucket : resumeBucket;
		try {
			LOG.info("Downloading freelancer's {} file from AWS S3", type);
			S3Object object = s3Client.getObject(bucketName, key);
			InputStream is = object.getObjectContent();
			byte[] content = FileCopyUtils.copyToByteArray(is);
			is.close();
			File out = new File(fileName);
			FileCopyUtils.copy(content, out);
			String contentType = Files.probeContentType(out.toPath());
			MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, content);
			LOG.info("Freelancer file downloaded from AWS S3: {}", fileName);
			return file;
		} catch  (SdkClientException e) {
			LOG.error("Error uploading object to AWS S3: {}", e.getMessage());
			throw new IOException(e);
		}
	}


	/*@Override
	public int hashCode() {
		final double prime = 31.0d;
		double result = 1;
		result = prime * result + ((avatarBucket == null) ? 0 : avatarBucket.hashCode());
		result = prime * result + ((resumeBucket == null) ? 0 : resumeBucket.hashCode());
		result = prime * result + ((s3Client == null) ? 0 : s3Client.hashCode());
		result = prime * result + ((util == null) ? 0 : util.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AWSS3ServiceImpl other = (AWSS3ServiceImpl) obj;
		if (avatarBucket == null) {
			if (other.avatarBucket != null)
				return false;
		} else if (!avatarBucket.equals(other.avatarBucket))
			return false;
		if (resumeBucket == null) {
			if (other.resumeBucket != null)
				return false;
		} else if (!resumeBucket.equals(other.resumeBucket))
			return false;
		if (s3Client == null) {
			if (other.s3Client != null)
				return false;
		} else if (!s3Client.equals(other.s3Client))
			return false;
		if (util == null) {
			if (other.util != null)
				return false;
		} else if (!util.equals(other.util))
			return false;
		return true;
	}*/

}
