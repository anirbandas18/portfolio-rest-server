package com.teenthofabud.portfolio.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.exception.ValidationException;
import com.teenthofabud.portfolio.vo.ErrorVO;
import com.teenthofabud.portfolio.vo.ExceptionVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	
	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<?> handleControllerException(ServiceException e) {
		String message = e.getMessage();
		message = message.replaceAll(exceptionCausePlaceholder, e.getReason());
		ErrorVO body = new ErrorVO(e.getStatus().value(), message);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, e.getStatus());
		return response;
	}
	
	@ExceptionHandler(value = {IOException.class})
	public ResponseEntity<?> handleSystemExceptions(Exception e) {
		ExceptionVO body = new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getName(), e.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
	
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		ValidationVO body = new ValidationVO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
	
}
