package com.teenthofabud.portfolio.handler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teenthofabud.portfolio.exception.ServiceException;
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
		ErrorVO body = new ErrorVO(e.getStatus().name(), message);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, e.getStatus());
		return response;
	}
	
	@ExceptionHandler(value = {IOException.class})
	public ResponseEntity<?> handleSystemExceptions(Exception e) {
		ExceptionVO body = new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getClass().getName(), e.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ErrorVO> response = new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<ErrorVO> errors = bindingResult.getAllErrors().stream()
				.map(x -> new ErrorVO(x.getObjectName(), x.getDefaultMessage())).collect(Collectors.toList());
		ValidationVO body = new ValidationVO(HttpStatus.BAD_REQUEST.name(), e.getMessage(), errors);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
	
}
