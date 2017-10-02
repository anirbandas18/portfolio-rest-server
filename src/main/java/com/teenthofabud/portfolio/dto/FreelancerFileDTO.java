package com.teenthofabud.portfolio.dto;

import java.io.IOException;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;

public class FreelancerFileDTO {
	
	private MultipartFile file;
	//private String baseFileLocation;
	//private File file;
	//private byte[] content;
	//private String fileName;
	//private String contentType;
	private FreelancerFile type;
	private String id;
	public FreelancerFile getType() {
		return type;
	}
	public void setType(FreelancerFile type) {
		this.type = type;
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
	@Override
	public String toString() {
		return "{fileName=" + file.getOriginalFilename() + ", fileSize=" + file.getSize() + ", type=" + type + ", id=" + id + "}";
	}
	@Override
	public int hashCode() {
		try {
			int result = Objects.hash(id, type, file.getOriginalFilename(), file.getBytes());
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
