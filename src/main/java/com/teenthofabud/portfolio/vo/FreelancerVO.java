package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "freelancer")
public class FreelancerVO {
	
	private String id;
	private String name;
	private Boolean changed;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getChanged() {
		return changed;
	}
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	
	
}
