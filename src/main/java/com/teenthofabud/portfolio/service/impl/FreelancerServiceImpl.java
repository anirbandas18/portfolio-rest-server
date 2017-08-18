package com.teenthofabud.portfolio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.configuration.constants.FreelancerFile;
import com.teenthofabud.portfolio.configuration.externalized.FreelancerExceptionMessages;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;

@Component
@Transactional(rollbackFor = { ServiceException.class })
public class FreelancerServiceImpl implements FreelancerService {

	@Autowired
	private FreelancerRepository repository;

	@Autowired
	private FreelancerExceptionMessages freelancerMessages;

	@Override
	public String createFreelancer(Freelancer freelancer) throws ServiceException {
		// TODO Auto-generated method stub
		Freelancer tmp = repository.insert(freelancer);
		return tmp.getId();
	}

	@Override
	public Freelancer readFreelancer(String id) throws ServiceException {
		// TODO Auto-generated method stub
		if (repository.exists(id)) {
			Freelancer freelancer = repository.findOne(id);
			return freelancer;
		} else {
			String cause = String.format(freelancerMessages.getDetailsEmpty(), id);
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean updateFreelancer(String id, Freelancer freelancer) throws ServiceException {
		// TODO Auto-generated method stub
		if (repository.exists(id)) {
			Freelancer updated = repository.save(freelancer);
			Integer or = freelancer.compareTo(updated);
			Boolean changed = or == 0 ? Boolean.FALSE : Boolean.TRUE;
			return changed;
		} else {
			String cause = String.format(freelancerMessages.getDetailsEmpty(), id);
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean deleteFreelancer(String id) throws ServiceException {
		// TODO Auto-generated method stub
		if (repository.exists(id)) {
			repository.delete(id);
			Boolean deleted = repository.exists(id);
			return !deleted;
		} else {
			String cause = String.format(freelancerMessages.getDetailsEmpty(), id);
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public FreelancerFileDTO exportFile(FreelancerFileDTO dto) throws ServiceException {
		// TODO Auto-generated method stub
		if (repository.exists(dto.getId())) {
			Freelancer freelancer = repository.findOne(dto.getId());
			String fileLocation = dto.getType().equals(FreelancerFile.RESUME) ? freelancer.getResumePath() : freelancer.getAvatarPath();
			try {
				Path filePath = Paths.get(fileLocation);
				byte[] content = Files.readAllBytes(filePath);
				String name = filePath.toFile().getName();
				String contentType = Files.probeContentType(filePath);
				MultipartFile file = new MockMultipartFile(name, name, contentType, content);
				dto.setFile(file);
				return dto;
			} catch (IOException e) {
				throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		} else {
			String cause = String.format(freelancerMessages.getDetailsEmpty(), dto.getId());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

	@Override
	public Boolean importFile(FreelancerFileDTO dto) throws ServiceException {
		// TODO Auto-generated method stub
		if (repository.exists(dto.getId())) {
			Path filePath = Paths.get(dto.getBaseFileLocation(), dto.getFile().getName());
			try {
				Path writtenPath = Files.write(filePath, dto.getFile().getBytes());
				Freelancer freelancer = repository.findOne(dto.getId());
				switch(dto.getType()) {
				case AVATAR :
					freelancer.setAvatarPath(writtenPath.toString());
					break;
				case RESUME :
					freelancer.setResumePath(writtenPath.toString());
					break;
				}
				Boolean updated = updateFreelancer(dto.getId(), freelancer);
				return updated;
			} catch (IOException e) {
				throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		} else {
			String cause = String.format(freelancerMessages.getDetailsEmpty(), dto.getId());
			throw new ServiceException(freelancerMessages.getExceptionTemplate(), HttpStatus.NOT_FOUND, cause);
		}
	}

}
