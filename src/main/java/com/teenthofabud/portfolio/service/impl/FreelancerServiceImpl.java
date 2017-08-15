package com.teenthofabud.portfolio.service.impl;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.teenthofabud.portfolio.configuration.externalized.FreelancerExceptionMessages;
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
	public String createFreelancer(Freelancer freelancer)  throws ServiceException{
		// TODO Auto-generated method stub
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsEmpty());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		Freelancer tmp = repository.save(freelancer);
		return tmp.get_id();
	}

	@Override
	public Freelancer readFreelancer(String id)  throws ServiceException {
		// TODO Auto-generated method stub
		Freelancer freelancer = repository.findOne(id);
		if(freelancer == null) {
			Exception cause = new Exception(String.format(exceptionMessages.getDetailsInvalid(), id));
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
		return freelancer;
	}

	@Override
	public Boolean updateFreelancer(Freelancer freelancer) throws ServiceException {
		// TODO Auto-generated method stub
		if(freelancer == null) {
			Exception cause = new Exception(exceptionMessages.getDetailsEmpty());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		} else if (freelancer.get_id().length() == 0) {
			Exception cause = new Exception(exceptionMessages.getIdInvalid());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		Freelancer updated = repository.save(freelancer);
		Integer or = freelancerComparator.compare(freelancer, updated);
		Boolean changed = or == 0 ? Boolean.FALSE : Boolean.TRUE;
		return changed;
	}

	@Override
	public Boolean deleteFreelancer(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	private Comparator<Freelancer> freelancerComparator = new Comparator<Freelancer>() {

		@Override
		public int compare(Freelancer o1, Freelancer o2) {
			// TODO Auto-generated method stub
			Integer id = o1.get_id().compareTo(o2.get_id());
			Integer firstName = o1.getFirstName().compareTo(o2.getFirstName());
			Integer lastName = o1.getLastName().compareTo(o2.getLastName());
			Integer emailId = o1.getEmailId().compareTo(o2.getEmailId());
			Integer phoneNumber = o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
			Integer state = o1.getCurrentLocation().getState().compareTo(o2.getCurrentLocation().getState());
			Integer country = o1.getCurrentLocation().getCountry().compareTo(o2.getCurrentLocation().getCountry());
			Integer or = id | firstName | lastName | emailId | phoneNumber | state | country;
			return or;
		}
		
	};
	
}
