package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;

public class EmptyFileException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7567737188107729705L;

	public EmptyFileException(FreelancerFile type, String fileName) {
		super(HttpStatus.BAD_REQUEST, type + " file:" + fileName + " is empty");
		// TODO Auto-generated constructor stub
	}

}
