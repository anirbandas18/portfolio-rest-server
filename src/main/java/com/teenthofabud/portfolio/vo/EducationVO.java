package com.teenthofabud.portfolio.vo;

import java.util.List;

import com.teenthofabud.portfolio.model.collections.Institution;

public class EducationVO {
	
	private List<Institution> schools;
	private List<Institution> universities;
	public List<Institution> getSchools() {
		return schools;
	}
	public void setSchools(List<Institution> schools) {
		this.schools = schools;
	}
	public List<Institution> getUniversities() {
		return universities;
	}
	public void setUniversities(List<Institution> universities) {
		this.universities = universities;
	}

}
