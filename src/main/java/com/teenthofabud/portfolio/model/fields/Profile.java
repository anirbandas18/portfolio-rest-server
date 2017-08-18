package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;

public class Profile implements Comparable<Profile>{
	
	private String name;
	private String overview;
	private String url;
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
