package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.exception.ServiceException;

@Service
public interface ResumeService {

	public byte[] exportResume(String freelancerId) throws ServiceException;
	
	public String importResume(MultipartFile resume, String freelancerId) throws ServiceException;
	
}
