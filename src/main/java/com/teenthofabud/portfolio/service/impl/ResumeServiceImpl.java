package com.teenthofabud.portfolio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.service.ResumeService;

@Component
public class ResumeServiceImpl implements ResumeService {
	
	@Value("${resume.base.location}")
	private String resumeBaseLocation;
	
	@Value("${resume.file.extension}")
	private String resumeFileExtension;
	
	@Value("${resume.exception.template}")
	private String resumeExceptionTemplate;
	
	public byte[] exportResume(String freelancerId) throws ServiceException {
		// TODO Auto-generated method stub
		String resumeName = freelancerId + resumeFileExtension;
		Path resumePath = Paths.get(resumeBaseLocation, resumeName);
		byte[] resume = new byte[0];
		try {
			resume = Files.readAllBytes(resumePath);
		} catch (IOException e) {
			throw new ServiceException(resumeExceptionTemplate, HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return resume;
	}

	public Long importResume(byte[] resume, String freelancerId) throws ServiceException {
		// TODO Auto-generated method stub
		String resumeName = freelancerId + resumeFileExtension;
		Path resumePath = Paths.get(resumeBaseLocation, resumeName);
		Path result = Paths.get("");
		try {
			result = Files.write(resumePath, resume);
		} catch (IOException e) {
			throw new ServiceException(resumeExceptionTemplate, HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		Long size = result.toFile().length();
		return size;
	}

}
