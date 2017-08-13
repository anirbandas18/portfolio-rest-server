package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.service.UtilityServices;

@Component
public class UtilityServicesImpl implements UtilityServices {
	
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	
	@Override
	public String wrapServiceException(ServiceException e) {
		String message = e.getMessage();
		message = message.replaceAll(exceptionCausePlaceholder, e.getCause().getMessage());
		return message;
	}

}
