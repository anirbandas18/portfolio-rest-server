package com.teenthofabud.portfolio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;

import com.teenthofabud.portfolio.exception.ResumeException;
import com.teenthofabud.portfolio.service.ResumeService;

public class ResumeServiceImpl implements ResumeService {
	
	@Value("${resume.base.location}")
	private String resumeBaseLocation;
	
	@Value("${resume.file.extension}")
	private String resumeFileExtension;
	
	public byte[] exportResume(String freelancerId) throws ResumeException {
		// TODO Auto-generated method stub
		String resumeName = freelancerId + resumeFileExtension;
		Path resumePath = Paths.get(resumeBaseLocation, resumeName);
		byte[] resume = null;
		try {
			resume = Files.readAllBytes(resumePath);
		} catch (IOException e) {
			throw new ResumeException("", e);
		}
		return resume;
	}

	public Long importResume(byte[] resume, String freelancerId) throws ResumeException {
		// TODO Auto-generated method stub
		String resumeName = freelancerId + resumeFileExtension;
		Path resumePath = Paths.get(resumeBaseLocation, resumeName);
		Path result = null;
		try {
			result = Files.write(resumePath, resume);
		} catch (IOException e) {
			result = resumePath;
			throw new ResumeException("", e);
		}
		Long size = result.toFile().length();
		return size;
	}

}
