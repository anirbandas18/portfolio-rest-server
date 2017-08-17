package com.teenthofabud.portfolio.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.service.UtilityServices;
import com.teenthofabud.portfolio.vo.ErrorVO;
import com.teenthofabud.portfolio.vo.ExceptionVO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private UtilityServices utilityServices;
	
	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<?> handleControllerException(ServiceException e) {
		String description = utilityServices.wrapServiceException(e);
		ErrorVO body = new ErrorVO(e.getStatus().value(), description);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, e.getStatus());
		return response;
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleOtherException(Exception e) {
		ExceptionVO body = new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getClass().getName(), e.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
	
}
