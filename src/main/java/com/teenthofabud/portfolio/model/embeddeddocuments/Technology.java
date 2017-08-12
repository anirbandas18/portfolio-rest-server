package com.teenthofabud.portfolio.model.embeddeddocuments;

import java.util.List;

import com.teenthofabud.portfolio.model.fields.Framework;
import com.teenthofabud.portfolio.model.fields.Proficiency;

public class Technology {

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
	
	
	
}
