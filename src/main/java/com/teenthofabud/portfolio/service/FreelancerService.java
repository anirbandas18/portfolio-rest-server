package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;

@Service
public interface FreelancerService {

	public String postFreelancer(Freelancer freelancer) throws ServiceException;
	
	public Freelancer getFreelancer(String id) throws ServiceException;
	
	
}
