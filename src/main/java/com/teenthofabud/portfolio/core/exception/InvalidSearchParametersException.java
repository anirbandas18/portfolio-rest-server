package com.teenthofabud.portfolio.core.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class InvalidSearchParametersException extends ConstraintViolationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805157609482533686L;

	public InvalidSearchParametersException(Class<?> clazz, Set<? extends ConstraintViolation<?>> constraintViolations) {
		super("The search parameters for: " + clazz.getSimpleName() + " is invalid!", constraintViolations);
		// TODO Auto-generated constructor stub
	}

}
