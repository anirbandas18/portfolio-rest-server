package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.teenthofabud.portfolio.model.fields.Employer;
import com.teenthofabud.portfolio.model.fields.Time;

@JsonRootName(value = "experience")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExperienceVO {
	
	private Employer under;
	private Time start;
	private Time end;
	private String name;
	private String as;
	private Double duration;
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
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	

}
