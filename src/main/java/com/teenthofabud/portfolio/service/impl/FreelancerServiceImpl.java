package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.teenthofabud.portfolio.configuration.externalized.FreelancerExceptionMessages;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.UtilityServices;

@Component
@Transactional(rollbackFor = {ServiceException.class})
public class FreelancerServiceImpl implements FreelancerService {
	
	@Autowired
	private FreelancerRepository repository;

	@Autowired
	private FreelancerExceptionMessages freelancerMessages; 
	
	@Autowired
	private UtilityServices utilityServices;

	@Override
	public String createFreelancer(Freelancer freelancer)  throws ServiceException{
		// TODO Auto-generated method stub
		if(freelancer == null) {
			Exception cause = new Exception(freelancerMessages.getDetailsInvalid());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		Freelancer tmp = repository.insert(freelancer);
		return tmp.getId();
	}

	@Override
	public Freelancer readFreelancer(String id)  throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(freelancerMessages.getIdInvalid());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			Freelancer freelancer = repository.findOne(id);
			return freelancer;
		} else  {
			Exception cause = new Exception(String.format(freelancerMessages.getDetailsEmpty(), id));
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean updateFreelancer(String id, Freelancer freelancer) throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(freelancerMessages.getIdInvalid());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			if(freelancer == null) {
				Exception cause = new Exception(freelancerMessages.getDetailsInvalid());
				throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
			}
			Freelancer updated = repository.save(freelancer);
			Integer or = utilityServices.compareFreelancers(freelancer, updated);
			Boolean changed = or == 0 ? Boolean.FALSE : Boolean.TRUE;
			return changed;
		} else  {
			Exception cause = new Exception(String.format(freelancerMessages.getDetailsEmpty(), id));
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean deleteFreelancer(String id) throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(freelancerMessages.getIdInvalid());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			repository.delete(id);
			return !repository.exists(id);
		} else  {
			Exception cause = new Exception(String.format(freelancerMessages.getDetailsEmpty(), id));
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

}
