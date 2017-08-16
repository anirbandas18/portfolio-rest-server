package com.teenthofabud.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.UtilityServices;

@Component
public class UtilityServicesImpl implements UtilityServices {
	
	@Value("${exception.cause.placeholder}")
	private String exceptionCausePlaceholder;
	
	@Override
	public String wrapServiceException(ServiceException e) {
		String message = e.getMessage();
		message = message.replaceAll(exceptionCausePlaceholder, e.getReason());
		return message;
	}

	@Override
	public Integer compareFreelancers(Freelancer o1, Freelancer o2) {
		// TODO Auto-generated method stub
		Integer id = o1.getId().compareTo(o2.getId());
		Integer firstName = o1.getFirstName().compareTo(o2.getFirstName());
		Integer lastName = o1.getLastName().compareTo(o2.getLastName());
		Integer emailId = o1.getEmailId().compareTo(o2.getEmailId());
		Integer phoneNumber = o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
		Integer state = o1.getCurrentLocation().getState().compareTo(o2.getCurrentLocation().getState());
		Integer country = o1.getCurrentLocation().getCountry().compareTo(o2.getCurrentLocation().getCountry());
		Integer city = o1.getCurrentLocation().getCity().compareTo(o2.getCurrentLocation().getCity());
		Integer or = id & firstName & lastName & emailId & phoneNumber & state & country & city;
		return or;
	}

}
