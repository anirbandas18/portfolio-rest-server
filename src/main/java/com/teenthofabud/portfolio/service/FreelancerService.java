package com.teenthofabud.portfolio.service;

import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;

@Service
public interface FreelancerService {

	public String createFreelancer(Freelancer freelancer) throws ServiceException;
	
	public Freelancer readFreelancer(String id) throws ServiceException;
	
	public Boolean updateFreelancer(String id, Freelancer freelancer) throws ServiceException;
	
	public Boolean deleteFreelancer(String id) throws ServiceException;
	
	public FreelancerFileDTO exportFile(FreelancerFileDTO dto) throws ServiceException;
	
	public Boolean importFile(FreelancerFileDTO dto) throws ServiceException;
	
	
}
