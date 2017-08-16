package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teenthofabud.portfolio.model.fields.Framework;
import com.teenthofabud.portfolio.model.fields.Proficiency;

@Document
public class Technology {

	@Id
	@JsonIgnore
	private String id;
	private String type;
	private String name;
	private Double duration;
	private Proficiency proficiency;
	private List<Framework> frameworks;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public Proficiency getProficiency() {
		return proficiency;
	}
	public void setProficiency(Proficiency proficiency) {
		this.proficiency = proficiency;
	}
	public List<Framework> getFrameworks() {
		return frameworks;
	}
	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
