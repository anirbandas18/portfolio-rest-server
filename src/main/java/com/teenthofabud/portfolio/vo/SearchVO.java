package com.teenthofabud.portfolio.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "search")
public class SearchVO {

	private Map<String,Object> parameters;
	private Integer sort;
	private String domain;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
