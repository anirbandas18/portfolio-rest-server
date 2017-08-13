package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.embeddeddocuments.Technology;

@Document
public class Project {
	
	@Id
	private String id;
	private Double duration;
	private String name;
	private String description;
	private List<String> responsibilities;
	private String link;
	private List<String> photoURLs;
	private List<Technology> technologies;
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
