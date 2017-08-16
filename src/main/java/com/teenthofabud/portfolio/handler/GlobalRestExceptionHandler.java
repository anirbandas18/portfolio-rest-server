package com.teenthofabud.portfolio.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.service.UtilityServices;
import com.teenthofabud.portfolio.vo.ErrorVO;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
	
	@Autowired
	private UtilityServices utilityServices;
	
	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<?> handleServiceException(ServiceException e) {
		String description = utilityServices.wrapServiceException(e);
		ErrorVO body = new ErrorVO(description);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, e.getStatus());
		return response;
	}
	
}
