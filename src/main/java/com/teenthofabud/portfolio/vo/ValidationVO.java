package com.teenthofabud.portfolio.vo;

import java.util.List;

public class ValidationVO extends ErrorVO {

	private List<String> errors;

	public ValidationVO(Integer code, String message, List<String> errors) {
		super(code, message);
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	
}
