package com.teenthofabud.portfolio.model.collections;

import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Time;

@Document
public class Institution {
	
	private String _id;
	private String name;
	private Time start;
	private Time end;
	private String major;
	private Double marks;
	private Double outOf;
	private String degree;
	private String logoURL;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getStart() {
		return start;
	}
	public void setStart(Time start) {
		this.start = start;
	}
	public Time getEnd() {
		return end;
	}
	public void setEnd(Time end) {
		this.end = end;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Double getMarks() {
		return marks;
	}
	public void setMarks(Double marks) {
		this.marks = marks;
	}
	public Double getOutOf() {
		return outOf;
	}
	public void setOutOf(Double outOf) {
		this.outOf = outOf;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
}
