package com.teenthofabud.portfolio.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.teenthofabud.portfolio.model.collections.Technology;

@JsonRootName(value = "technologystack")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StackVO {
	
	private List<Technology> frontend;
	private List<Technology> database;
	private List<Technology> container;
	private Technology backend;
	public List<Technology> getFrontend() {
		return frontend;
	}
	public void setFrontend(List<Technology> frontend) {
		this.frontend = frontend;
	}
	public List<Technology> getDatabase() {
		return database;
	}
	public void setDatabase(List<Technology> database) {
		this.database = database;
	}
	public List<Technology> getContainer() {
		return container;
	}
	public void setContainer(List<Technology> container) {
		this.container = container;
	}
	public Technology getBackend() {
		return backend;
	}
	public void setBackend(Technology backend) {
		this.backend = backend;
	}
	
}
