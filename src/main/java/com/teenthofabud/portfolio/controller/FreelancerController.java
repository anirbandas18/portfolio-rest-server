package com.teenthofabud.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private FreelancerService freelancerService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerIntroduction(@PathVariable String id) throws ServiceException {
		Freelancer body = freelancerService.getFreelancer(id);
		ResponseEntity<Freelancer> response = new ResponseEntity<>(body, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(@PathVariable String id) throws ServiceException {
		byte[] resume = resumeService.exportResume(id);
		Resource resource = new ByteArrayResource(resume);
		ResponseEntity<Resource> response = ResponseEntity.ok()
				.contentLength(resume.length)
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
				.body(resource);
		return response;
	}
	
}
