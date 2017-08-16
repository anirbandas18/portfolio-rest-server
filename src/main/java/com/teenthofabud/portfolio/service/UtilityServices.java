package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;

@Service
public interface UtilityServices {

	public String wrapServiceException(ServiceException e);
	
	public Integer compareFreelancers(Freelancer o1, Freelancer o2);
	
}
