package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.teenthofabud.portfolio.service.UtilityServices;

public class UtilityServicesImpl implements UtilityServices {
	
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	
	@Override
	public String wrapException(Exception e) {
		String message = e.getMessage();
		message = message.replaceAll(exceptionCausePlaceholder, e.getCause().getMessage());
		return message;
	}

}
