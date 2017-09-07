package com.teenthofabud.portfolio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.exception.FreelancerAlreadyExistsException;
import com.teenthofabud.portfolio.core.exception.FreelancerNotFoundException;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;

@Component
@Transactional(rollbackFor = { FreelancerAlreadyExistsException.class, FreelancerNotFoundException.class, IOException.class })
public class FreelancerServiceImpl implements FreelancerService {

	@Autowired
	private FreelancerRepository repository;

	@Autowired
	private ExampleMatcher matcher;
	
	@Autowired
	private Sort asc;
	
	@Autowired
	private UtilityServiceImpl util;
	
	private static final Logger LOG = LoggerFactory.getLogger(FreelancerService.class);
	
	@Override
	public String create(Freelancer freelancer) throws FreelancerAlreadyExistsException {
		// TODO Auto-generated method stub
		String id = String.valueOf(freelancer.hashCode());
		LOG.info("Creating freelancer with id: {}", id);
		if(repository.exists(id)) {
			LOG.error("Freelancer with id: {} already exists", id);
			throw new FreelancerAlreadyExistsException(id);
		} else {
			freelancer.setId(id);
			Freelancer tmp = repository.insert(freelancer);
			LOG.info("Freelancer created successfully!");
			return tmp.getId();
		}
	}

	@Override
	public Freelancer read(String id) throws FreelancerNotFoundException {
		// TODO Auto-generated method stub
		LOG.info("Reading freelancer with id: {}", id);
		if (repository.exists(id)) {
			Freelancer freelancer = repository.findOne(id);
			LOG.info("Freelancer read successfully");
			return freelancer;
		} else {
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("id", id);
			LOG.error("Freelancer not found");
			throw new FreelancerNotFoundException(parameters);
		}
	}

	@Override
	public Boolean update(String id, Freelancer freelancer) throws FreelancerNotFoundException {
		// TODO Auto-generated method stub
		LOG.info("Updating freelancer with id: {}", id);
		if (repository.exists(id)) {
			Freelancer previous = repository.findOne(id);
			Freelancer updated = repository.save(freelancer);
			Integer or = previous.compareTo(updated);
			Boolean changed = or == 0 ? Boolean.FALSE : Boolean.TRUE;
			LOG.info("Updation status: {} of freelancer with id: {}", changed, id);
			return changed;
		} else {
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("id", id);
			LOG.error("Freelancer not found");
			throw new FreelancerNotFoundException(parameters);
		}
	}

	@Override
	public Boolean delete(String id) throws FreelancerNotFoundException  {
		// TODO Auto-generated method stub
		LOG.info("Deleting freelancer with id: {}", id);
		if (repository.exists(id)) {
			repository.delete(id);
			Boolean deleted = repository.exists(id);
			LOG.info("Deletion status: {} of freelancer with id: {}", !deleted, id);
			return !deleted;
		} else {
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("id", id);
			LOG.error("Freelancer not found");
			throw new FreelancerNotFoundException(parameters);
		}
	}

	@Override
	public FreelancerFileDTO exportFile(FreelancerFileDTO dto) throws FreelancerNotFoundException, IOException {
		// TODO Auto-generated method stub
		String id = dto.getId();
		LOG.info("Exporting {} file of freelancer with id: {}", dto.getType(), id);
		if (repository.exists(id)) {
			Freelancer freelancer = repository.findOne(id);
			String fileLocation = dto.getType().equals(FreelancerFile.RESUME) ? freelancer.getResumePath() : freelancer.getAvatarPath();
			Path filePath = Paths.get(fileLocation);
			byte[] content = Files.readAllBytes(filePath);
			LOG.info("{} file of freelancer read from {}", dto.getType(), filePath);
			String name = filePath.toFile().getName();
			String contentType = Files.probeContentType(filePath);
			LOG.info("{} file metadata = size: {}, type: {}", dto.getType(), content.length, contentType);
			MultipartFile file = new MockMultipartFile(name, name, contentType, content);
			dto.setFile(file);
			return dto;
		} else {
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("id", id);
			LOG.error("Freelancer not found");
			throw new FreelancerNotFoundException(parameters);
		}
	}

	@Override
	public Boolean importFile(FreelancerFileDTO dto) throws IOException, FreelancerNotFoundException {
		// TODO Auto-generated method stub
		String id = dto.getId();
		LOG.info("Importing {} file of freelancer with id: {}", dto.getType(), id);
		if (repository.exists(id)) {
			Path filePath = Paths.get(dto.getBaseFileLocation(), id, dto.getType().name().toLowerCase(), dto.getFile().getName());
			Path writtenPath = Files.write(filePath, dto.getFile().getBytes());
			LOG.info("{} file of freelancer written to {}", dto.getType(), filePath);
			Freelancer freelancer = repository.findOne(dto.getId());
			switch(dto.getType()) {
			case AVATAR :
				freelancer.setAvatarPath(writtenPath.toString());
				LOG.info("Updating avatar file path of freelancer");
				break;
			case RESUME :
				freelancer.setResumePath(writtenPath.toString());
				LOG.info("Updating resume file path of freelancer");
				break;
			}
			Boolean updated = update(id, freelancer);
			LOG.info("Updation status: {} of freelancer with id: {}", updated, id);
			return updated;
		} else {
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("id", id);
			LOG.error("Freelancer not found");
			throw new FreelancerNotFoundException(parameters);
		}
	}

	@Override
	public Freelancer read(Detail freelancerDetails) throws FreelancerNotFoundException {
		// TODO Auto-generated method stub
		Map<String,Object> map = util.pojo2Map(freelancerDetails);
		LOG.info("Reading freelancer with details: {}", map);
		Freelancer freelancer = new Freelancer();
		freelancer.setDetail(freelancerDetails);
		Example<Freelancer> query = Example.of(freelancer, matcher);
		Freelancer tmp = repository.findOne(query);
		if(tmp != null) {
			LOG.info("Freelancer found with id: {} matching to criteria", tmp.getId());
			return tmp;
		} else {
			LOG.error("No freelancer found with matching criteria");
			throw new FreelancerNotFoundException(util.pojo2Map(freelancerDetails));
		}
	}

	@Override
	public List<Freelancer> readAll(Detail freelancerDetails, Sort...order) throws FreelancerNotFoundException {
		// TODO Auto-generated method stub
		Map<String,Object> map = util.pojo2Map(freelancerDetails);
		Sort x = order.length == 0 ? asc : order[0];
		LOG.info("Reading freelancer with details: {} in order: {}", map, x);
		Freelancer freelancer = new Freelancer();
		freelancer.setDetail(freelancerDetails);
		Example<Freelancer> query = Example.of(freelancer, matcher);
		List<Freelancer> tmp = repository.findAll(query, x);
		if(!tmp.isEmpty()) {
			LOG.info("{} freelancers found with matching criteria", tmp.size());
			return tmp;
		} else {
			LOG.error("No freelancers found with matching criteria");
			throw new FreelancerNotFoundException(map);
		}
	}

	@Override
	public List<Freelancer> readAll(Sort... order) {
		// TODO Auto-generated method stub
		List<Freelancer> result = new ArrayList<>();
		Sort x = order.length == 0 ? asc : order[0];
		LOG.info("Reading all freelancers in order: {}", x);
		result = repository.findAll(x);
		LOG.info("{} freelancers found", result.size());
		return result;
	}

	
}
