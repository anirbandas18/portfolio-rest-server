package com.teenthofabud.portfolio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.exception.FreelancerAlreadyExistsException;
import com.teenthofabud.portfolio.core.exception.FreelancerFileNotFoundException;
import com.teenthofabud.portfolio.core.exception.FreelancerNotFoundException;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.model.fields.Detail;

@Service
public interface FreelancerService {

	public String create(Freelancer freelancer) throws FreelancerAlreadyExistsException;
	
	public Freelancer read(String id) throws FreelancerNotFoundException;
	
	public Freelancer read(Detail freelancerDetails) throws FreelancerNotFoundException;
	
	public List<Freelancer> readAll(Detail freelancerDetails, Sort...order) throws FreelancerNotFoundException;
	
	public List<Freelancer> readAll(Sort...order);
	
	public Boolean update(String id, Freelancer freelancer) throws FreelancerNotFoundException;
	
	public Boolean delete(String id) throws FreelancerNotFoundException;
	
	public FreelancerFileDTO exportFile(String id, FreelancerFile type) throws IOException, FreelancerNotFoundException, FreelancerFileNotFoundException ;
	
	public Boolean importFile(FreelancerFileDTO dto) throws IOException, FreelancerNotFoundException;
	
	
}
