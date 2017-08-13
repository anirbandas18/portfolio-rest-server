package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.teenthofabud.portfolio.configuration.FreelancerExceptionMessages;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;

@Component
@Transactional(rollbackFor = {ServiceException.class})
public class FreelancerServiceImpl implements FreelancerService {
	
	@Autowired
	private FreelancerRepository repository;

	@Autowired
	private FreelancerExceptionMessages exceptionMessages; 

	@Override
	public String postFreelancer(Freelancer freelancer)  throws ServiceException{
		// TODO Auto-generated method stub
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsEmpty());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		Freelancer tmp = repository.save(freelancer);
		return tmp.getId();
	}

	@Override
	public Freelancer getFreelancer(String id)  throws ServiceException {
		// TODO Auto-generated method stub
		Freelancer freelancer = repository.findOne(id);
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsInvalid() + id);
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
		return freelancer;
	}

}
