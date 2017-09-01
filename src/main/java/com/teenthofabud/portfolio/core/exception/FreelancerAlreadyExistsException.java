package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class FreelancerAlreadyExistsException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5103676734250790857L;

	
	public FreelancerAlreadyExistsException(String id) {
		super(HttpStatus.CONFLICT, "The freelancer with ID: " + id + " already exists");
		// TODO Auto-generated constructor stub
	}

}
