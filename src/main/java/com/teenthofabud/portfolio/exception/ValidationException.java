package com.teenthofabud.portfolio.exception;

import java.util.List;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6129437939036030858L;
	
	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ValidationException(List<String> errors) {
		super("Field validation errors " + errors.size());
		this.errors = errors;
	}

	

}
