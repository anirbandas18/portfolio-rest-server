package com.teenthofabud.portfolio.model.collections;

import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Employer;
import com.teenthofabud.portfolio.model.fields.Time;

@Document
public class Experience {
	
	private String _id;
	private Employer under;
	private Time start;
	private Time end;
	private String name;
	private String as;
	public Employer getUnder() {
		return under;
	}
	public void setUnder(Employer under) {
		this.under = under;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAs() {
		return as;
	}
	public void setAs(String as) {
		this.as = as;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

}
