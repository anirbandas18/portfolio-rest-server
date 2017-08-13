package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

@Service
public interface UtilityServices {

	public String wrapException(Exception e);
	
}
