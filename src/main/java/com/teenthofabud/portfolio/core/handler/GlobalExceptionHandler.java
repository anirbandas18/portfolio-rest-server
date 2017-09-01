package com.teenthofabud.portfolio.core.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartException;

import com.teenthofabud.portfolio.vo.ApiResponse;
import com.teenthofabud.portfolio.vo.ValidationVO;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	
	@Value("${validation.error.template}")
	private String validationErrorTemplate;
	
	@ExceptionHandler(value = { HttpStatusCodeException.class })
	public ResponseEntity<ApiResponse> handleControllerExceptions(HttpStatusCodeException e) {
		ApiResponse  body = new ApiResponse(e.getMessage());
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ResponseEntity<ApiResponse> response = new ResponseEntity<>(body, e.getStatusCode());
		return response;
	}
	
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationVO> handleValidationErrors(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<ApiResponse> errors = bindingResult.getFieldErrors().stream()
				.map(x -> new ApiResponse(x.getField(), x.getDefaultMessage())).collect(Collectors.toList());
		String message = validationErrorTemplate.replace(exceptionCausePlaceholder, bindingResult.getObjectName());
		ValidationVO body = new ValidationVO(message, errors);
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(value = MultipartException.class)
	public ResponseEntity<ValidationVO> handleMultipartFileException(MultipartException e) {
		ValidationVO body = new ValidationVO(e.getMessage());
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ResponseEntity<ValidationVO> response = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		return response;
	}
	
}
