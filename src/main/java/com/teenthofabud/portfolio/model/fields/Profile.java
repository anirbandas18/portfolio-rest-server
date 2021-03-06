package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;

import javax.validation.constraints.Pattern;

import com.teenthofabud.portfolio.core.validation.groups.RequestBodyValidation;


public class Profile implements Comparable<Profile>{
	
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "profile name should only contain alphanumeric characters and space", groups = RequestBodyValidation.class)	
	private String name;
	private String overview;
	private String url;// validate by GET method
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int compareTo(Profile o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Profile::getName)
				.thenComparing(Profile::getOverview)
				.thenComparing(Profile::getUrl)
				.compare(this, o);
	}
	
}
