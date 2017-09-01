package com.teenthofabud.portfolio.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "validation")
@JsonInclude(value = Include.NON_EMPTY)
public class ValidationVO  {

	private List<ResponseVO> errors;
	private String message;

	public List<ResponseVO> getErrors() {
		return errors;
	}

	public void setErrors(List<ResponseVO> errors) {
		this.errors = errors;
	}

	public ValidationVO(String message, List<ResponseVO> errors) {
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
