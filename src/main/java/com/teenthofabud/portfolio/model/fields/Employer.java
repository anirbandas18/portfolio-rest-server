package com.teenthofabud.portfolio.model.fields;

import com.teenthofabud.portfolio.model.fields.Location;

public class Employer {
	
	private String name;
	private Location location;
	private String logoURL;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	

}
