package com.teenthofabud.portfolio.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "search")
@JsonInclude(value = Include.NON_EMPTY)
public class SearchVO {

	private Map<String,Object> parameters;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
