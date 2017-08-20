package com.teenthofabud.portfolio.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;
	private String reason;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public ServiceException(String message, HttpStatus status, String reason) {
		super();
		this.message = message;
		this.status = status;
		this.reason = reason;
	}
	
}
