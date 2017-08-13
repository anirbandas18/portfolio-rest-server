package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.ResumeException;

@Service
public interface ResumeService {

	public byte[] exportResume(String freelancerId) throws ResumeException;
	
	public Long importResume(byte[] resume, String freelancerId)  throws ResumeException;
	
}
