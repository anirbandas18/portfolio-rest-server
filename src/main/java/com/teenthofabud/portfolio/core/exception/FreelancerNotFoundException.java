package com.teenthofabud.portfolio.core.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class FreelancerNotFoundException extends HttpStatusCodeException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2178855058682578470L;

	public FreelancerNotFoundException(Map<String,Object> parameters) {
		super(HttpStatus.NOT_FOUND, "The Freelancer with " + parameters + " not found");
	}

}
