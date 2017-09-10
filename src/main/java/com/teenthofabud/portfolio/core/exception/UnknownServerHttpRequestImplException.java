package com.teenthofabud.portfolio.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.client.HttpStatusCodeException;

public class UnknownServerHttpRequestImplException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6793128846917399140L;

	public UnknownServerHttpRequestImplException(Class<? extends ServerHttpRequest> implClass) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "Unchecked ServerHttpRequest implementation: " + implClass.getSimpleName());
		// TODO Auto-generated constructor stub
	}


}
