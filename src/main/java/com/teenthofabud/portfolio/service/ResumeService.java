package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.ServiceException;

@Service
public interface ResumeService {

	public byte[] exportResume(String freelancerId) throws ServiceException;
	
	public Long importResume(byte[] resume, String freelancerId)  throws ServiceException;
	
}
