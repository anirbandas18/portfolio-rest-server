package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Time;

@Document
public class Project {
	
	private String _id;
	private Time from;
	private Time to;
	private String name;
	private String description;
	private List<String> responsibilities;
	private String link;
	private List<String> photoURLs;
	private List<Technology> technologies;
	private Integer teamSize;
	
	public Time getFrom() {
		return from;
	}
	public void setFrom(Time from) {
		this.from = from;
	}
	public Time getTo() {
		return to;
	}
	public void setTo(Time to) {
		this.to = to;
	}
	public Integer getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(Integer teamSize) {
		this.teamSize = teamSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getResponsibilities() {
		return responsibilities;
	}
	public void setResponsibilities(List<String> responsibilities) {
		this.responsibilities = responsibilities;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<String> getPhotoURLs() {
		return photoURLs;
	}
	public void setPhotoURLs(List<String> photoURLs) {
		this.photoURLs = photoURLs;
	}
	public List<Technology> getTechnologies() {
		return technologies;
	}
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	

}
