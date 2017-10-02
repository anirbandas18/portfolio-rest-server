package com.teenthofabud.portfolio.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.dto.FileArchiveMetadata;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;

@Service
public interface FileArchiveService {
	
	public FileArchiveMetadata uploadFileToBucket(FreelancerFileDTO dto) throws IOException;
	
	public MultipartFile downloadFileFromBucket(String key, FreelancerFile type) throws IOException;

}
