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
			Exception cause = new Exception(exceptionMessages.getDetailsInvalid());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		Freelancer tmp = repository.insert(freelancer);
		return tmp.getId();
	}

	@Override
	public Freelancer readFreelancer(String id)  throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(exceptionMessages.getIdInvalid());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			Freelancer freelancer = repository.findOne(id);
			return freelancer;
		} else  {
			Exception cause = new Exception(String.format(exceptionMessages.getDetailsEmpty(), id));
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean updateFreelancer(String id, Freelancer freelancer) throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(exceptionMessages.getIdInvalid());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			if(freelancer == null) {
				Exception cause = new Exception(exceptionMessages.getDetailsInvalid());
				throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
			}
			Freelancer updated = repository.save(freelancer);
			Integer or = freelancerComparator.compare(freelancer, updated);
			Boolean changed = or == 0 ? Boolean.FALSE : Boolean.TRUE;
			return changed;
		} else  {
			Exception cause = new Exception(String.format(exceptionMessages.getDetailsEmpty(), id));
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean deleteFreelancer(String id) throws ServiceException {
		// TODO Auto-generated method stub
		if (id == null || id.length() == 0) {
			Exception cause = new Exception(exceptionMessages.getIdInvalid());
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.BAD_REQUEST, cause);
		}
		if(repository.exists(id)) {
			repository.delete(id);
			return !repository.exists(id);
		} else  {
			Exception cause = new Exception(String.format(exceptionMessages.getDetailsEmpty(), id));
			throw new ServiceException(exceptionMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	private Comparator<Freelancer> freelancerComparator = new Comparator<Freelancer>() {

		@Override
		public int compare(Freelancer o1, Freelancer o2) {
			// TODO Auto-generated method stub
			Integer id = o1.getId().compareTo(o2.getId());
			Integer firstName = o1.getFirstName().compareTo(o2.getFirstName());
			Integer lastName = o1.getLastName().compareTo(o2.getLastName());
			Integer emailId = o1.getEmailId().compareTo(o2.getEmailId());
			Integer phoneNumber = o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
			Integer state = o1.getCurrentLocation().getState().compareTo(o2.getCurrentLocation().getState());
			Integer country = o1.getCurrentLocation().getCountry().compareTo(o2.getCurrentLocation().getCountry());
			Integer city = o1.getCurrentLocation().getCity().compareTo(o2.getCurrentLocation().getCity());
			Integer or = id | firstName | lastName | emailId | phoneNumber | state | country | city;
			return or;
		}
		
	};
	
}
