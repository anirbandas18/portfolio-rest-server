package com.teenthofabud.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FileArchiveMetadata {
	
	@JsonIgnore
	private String key;
	private String url;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public FileArchiveMetadata(String key, String url) {
		super();
		this.key = key;
		this.url = url;
	}
	public FileArchiveMetadata() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
