package com.teenthofabud.portfolio.model.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Resume {
	
	@Id
	@JsonIgnore
	private String id;
	private String freelancerId;
	private String path;
	private String name;
	private String extension;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFreelancerId() {
		return freelancerId;
	}
	public void setFreelancerId(String freelancerId) {
		this.freelancerId = freelancerId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	

}
