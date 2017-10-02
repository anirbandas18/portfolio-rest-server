package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;

public class FreelancerFileNotFoundException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8512876529942365924L;
	
	public FreelancerFileNotFoundException(String id, FreelancerFile type) {
		super(HttpStatus.NOT_FOUND, "No " + type + " file associated with freelancer id: " + id);
	}

}
