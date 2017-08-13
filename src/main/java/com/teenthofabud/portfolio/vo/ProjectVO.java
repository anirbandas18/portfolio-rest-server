package com.teenthofabud.portfolio.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.teenthofabud.portfolio.model.collections.Technology;
import com.teenthofabud.portfolio.model.fields.Time;

@JsonRootName(value = "project")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectVO {
	
	private Time from;
	private Time to;
	private String name;
	private String description;
	private List<String> responsibilities;
	private String link;
	private List<String> photoURLs;
	private List<Technology> technologies;
	private Integer teamSize;
	private Double duration;
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
	public Integer getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(Integer teamSize) {
		this.teamSize = teamSize;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
	

}
