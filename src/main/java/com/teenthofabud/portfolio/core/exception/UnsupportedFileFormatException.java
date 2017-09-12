package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;

public class UnsupportedFileFormatException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9150637188159795780L;

	public UnsupportedFileFormatException(FreelancerFile type, String fileFormat) {
		super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported " + type + " file format: " + fileFormat);

	}

}
