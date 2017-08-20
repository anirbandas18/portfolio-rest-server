package com.teenthofabud.portfolio.vo;

import java.util.List;

public class ValidationVO extends ErrorVO {

	private List<ErrorVO> errors;

	public List<ErrorVO> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorVO> errors) {
		this.errors = errors;
	}

	public ValidationVO(String code, String message, List<ErrorVO> errors) {
		super(code, message);
		this.errors = errors;
	}

	
}
