package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.exception.FreelancerException;
import com.teenthofabud.portfolio.model.collections.Freelancer;

@Service
public interface FreelancerService {

	public String postFreelancer(Freelancer freelancer) throws FreelancerException;
	
	public Freelancer getFreelancer(String id) throws FreelancerException;
	
	
}
