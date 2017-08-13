package com.teenthofabud.portfolio.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;
	private Throwable cause;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Throwable getCause() {
		return cause;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	public ServiceException(String message, HttpStatus status, Throwable cause) {
		super();
		this.message = message;
		this.status = status;
		this.cause = cause;
	}
	
}
