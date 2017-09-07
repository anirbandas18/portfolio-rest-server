package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidSearchParametersException extends HttpStatusCodeException {

	public InvalidSearchParametersException(String schemaName) {
		super(HttpStatus.BAD_REQUEST, "The search parameters doesn't confront to the schema of: " + schemaName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805157609482533686L;

}
