package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teenthofabud.portfolio.configuration.FreelancerExceptionMessages;
import com.teenthofabud.portfolio.exception.FreelancerException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;

@Component
public class FreelancerServiceImpl implements FreelancerService {
	
	@Autowired
	private FreelancerRepository repository;

	@Autowired
	private FreelancerExceptionMessages exceptionMessages; 

	@Override
	public String postFreelancer(Freelancer freelancer)  throws FreelancerException{
		// TODO Auto-generated method stub
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsEmpty());
			throw new FreelancerException(exceptionMessages.getExceptionTemplate(), cause);
		}
		Freelancer tmp = repository.save(freelancer);
		return tmp.getId();
	}

	@Override
	public Freelancer getFreelancer(String id)  throws FreelancerException {
		// TODO Auto-generated method stub
		Freelancer freelancer = repository.findById(id);
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsInvalid() + id);
			throw new FreelancerException(exceptionMessages.getExceptionTemplate(), cause);
		}
		return freelancer;
	}

}
