package com.teenthofabud.portfolio.core.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class InvalidSearchParametersException extends ConstraintViolationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805157609482533686L;

	public InvalidSearchParametersException(Set<? extends ConstraintViolation<?>> errors) {
		super("The search parametrs are not valid", errors);
	}
	
	
}
