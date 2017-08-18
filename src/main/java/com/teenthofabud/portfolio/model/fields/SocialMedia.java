package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;

public class SocialMedia implements Comparable<SocialMedia>{
	
	private String link;
	private String name;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int compareTo(SocialMedia o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(SocialMedia::getName).compare(this, o);
	}
	
	

}
