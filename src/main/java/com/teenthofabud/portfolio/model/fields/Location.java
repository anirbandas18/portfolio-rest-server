package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;

public class Location implements Comparable<Location> {

	private String state;
	private String country;
	private String city;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Location::getCity)
				.thenComparing(Location::getState)
				.thenComparing(Location::getState)
				.compare(this, o);
	}

}
