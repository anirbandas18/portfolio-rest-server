package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidSearchParametersException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805157609482533686L;

	protected InvalidSearchParametersException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "The search parametrs are not valid");
	}

}
