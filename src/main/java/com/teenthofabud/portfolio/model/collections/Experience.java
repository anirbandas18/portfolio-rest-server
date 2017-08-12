package com.teenthofabud.portfolio.model.collections;

import com.teenthofabud.portfolio.model.embeddeddocuments.Employer;
import com.teenthofabud.portfolio.model.fields.Time;

public class Experience {
	
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
	
	

}
