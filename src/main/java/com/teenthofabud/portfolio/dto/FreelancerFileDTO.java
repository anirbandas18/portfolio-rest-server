package com.teenthofabud.portfolio.dto;

import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.configuration.constants.FreelancerFile;

public class FreelancerFileDTO {
	
	private MultipartFile file;
	private FreelancerFile type;
	private String baseFileLocation;
	private String id;
	public FreelancerFile getType() {
		return type;
	}
	public void setType(FreelancerFile type) {
		this.type = type;
	}
	public String getBaseFileLocation() {
		return baseFileLocation;
	}
	public void setBaseFileLocation(String baseFileLocation) {
		this.baseFileLocation = baseFileLocation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
