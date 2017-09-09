package com.teenthofabud.portfolio.vo;

import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "search")
@JsonInclude(value = Include.NON_EMPTY)
public class SearchVO {

	@NotEmpty(message = "Search parameters cannot be null")
	@Valid
	private Map<String,Object> parameters;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return parameters.toString();
	}
	
	

}
