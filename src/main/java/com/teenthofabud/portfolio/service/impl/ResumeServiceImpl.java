package com.teenthofabud.portfolio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.configuration.externalized.ResumeExceptionMessages;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.ResumeService;

@Component
@Transactional(rollbackFor = { ServiceException.class })
public class ResumeServiceImpl implements ResumeService {

	@Value("${resume.file.location}")
	private String resumeFileLocation;

	@Value("${resume.file.extension}")
	private String resumeFileExtension;

	@Autowired
	private ResumeExceptionMessages resumeMessages;

	@Autowired
	private FreelancerRepository freelancerRepository;

	public byte[] exportResume(String freelancerId) throws ServiceException {
		// TODO Auto-generated method stub
		if (freelancerId == null || freelancerId.length() == 0) {
			throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST,
					resumeMessages.getIdEmpty());
		}
		if (freelancerRepository.exists(freelancerId)) {
			String resumeName = freelancerId + resumeFileExtension;
			Path resumePath = Paths.get(resumeFileLocation, resumeName);
			byte[] resume = new byte[0];
			try {
				resume = Files.readAllBytes(resumePath);
			} catch (IOException e) {
				throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage());
			}
			return resume;
		} else {
			throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND,
					String.format(resumeMessages.getIdInvalid(), freelancerId));
		}
	}

	public String importResume(MultipartFile resume, String freelancerId) throws ServiceException {
		// TODO Auto-generated method stub
		if (freelancerId == null || freelancerId.length() == 0) {
			throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST,
					resumeMessages.getIdEmpty());
		} else if (resume.isEmpty() || resume.getSize() == 0l) {
			throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.UNPROCESSABLE_ENTITY,
					resumeMessages.getContentAbsent());
		}

		if (freelancerRepository.exists(freelancerId)) {
			String resumeName = freelancerId + resumeFileExtension;
			Path resumePath = Paths.get(resumeFileLocation, resumeName);
			String md5 = "";
			try {
				Files.write(resumePath, resume.getBytes());
				md5 = DigestUtils.md5DigestAsHex(resume.getBytes());
			} catch (IOException e) {
				throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage());
			}
			return md5;
		} else {
			throw new ServiceException(resumeMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND,
					String.format(resumeMessages.getIdInvalid(), freelancerId));
		}
	}

}
