package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "freelancer")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FreelancerVO {
	
	private String name;
	private String id;
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
