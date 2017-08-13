package com.teenthofabud.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.ResumeService;
import com.teenthofabud.portfolio.service.UtilityServices;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private FreelancerService freelancerService;
	
	@Autowired
	private UtilityServices utilityServices;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFreelancerIntroduction(@PathVariable String id) {
		ResponseEntity<?> response = null;
		try {
			Freelancer body = freelancerService.getFreelancer(id);
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} catch (ServiceException e) {
			String description = utilityServices.wrapServiceException(e);
			Resource resource = new DescriptiveResource(description);
			response = new ResponseEntity<>(resource, e.getStatus());
		}
		return response;
	}
	
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(@PathVariable String id) {
		ResponseEntity<Resource> response = null;
		try {
			byte[] resume = resumeService.exportResume(id);
			Resource resource = new ByteArrayResource(resume);
			response = ResponseEntity.ok()
					.contentLength(resume.length)
					.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
					.body(resource);
		} catch (ServiceException e) {
			String description = utilityServices.wrapServiceException(e);
			Resource resource = new DescriptiveResource(description);
			response = new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
}
