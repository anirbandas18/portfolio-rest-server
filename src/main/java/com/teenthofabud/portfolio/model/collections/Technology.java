package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Framework;
import com.teenthofabud.portfolio.model.fields.Proficiency;

@Document
public class Technology {

	private String _id;
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
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
