package com.teenthofabud.portfolio.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.teenthofabud.portfolio.service.UtilityServices;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private UtilityServices utilityServices;

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Resource> handleControllerExceptions(ServiceException e) {
		String description = utilityServices.wrapServiceException(e);
		Resource resource = new DescriptiveResource(description);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		ResponseEntity<Resource> response = new ResponseEntity<>(resource, headers, e.getStatus());
		return response;
	}
	
}
