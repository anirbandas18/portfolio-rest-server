package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class EmptySearchParametersException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5835756670978729205L;

	public EmptySearchParametersException() {
		super(HttpStatus.BAD_REQUEST, "No search parameters provided");
		// TODO Auto-generated constructor stub
	}

}
