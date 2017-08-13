package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.ServiceException;

@Service
public interface UtilityServices {

	public String wrapServiceException(ServiceException e);
	
}
