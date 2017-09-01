package com.teenthofabud.portfolio.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "validation")
@JsonInclude(value = Include.NON_EMPTY)
public class ValidationVO  {

	private List<ApiResponse> errors;
	private String message;

	public List<ApiResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ApiResponse> errors) {
		this.errors = errors;
	}

	public ValidationVO(String message, List<ApiResponse> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ValidationVO(String message) {
		super();
		this.message = message;
	}

	
	
}
