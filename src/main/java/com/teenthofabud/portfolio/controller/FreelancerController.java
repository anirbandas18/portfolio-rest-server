package com.teenthofabud.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.ResumeService;
import com.teenthofabud.portfolio.vo.ResumeVO;
import com.teenthofabud.portfolio.vo.FreelancerVO;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private FreelancerService freelancerService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(@PathVariable String id) throws ServiceException {
		Freelancer freelancer = freelancerService.getFreelancer(id);
		ResponseEntity<Freelancer> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(freelancer);
		return response;
	}
	
	@PostMapping
	public ResponseEntity<FreelancerVO> postFreelancerDetails(@RequestBody Freelancer freelancer) throws ServiceException {
		String id = freelancerService.postFreelancer(freelancer);
		String name = freelancer.getFirstName() + " " + freelancer.getLastName();
		FreelancerVO f = new FreelancerVO();
		f.setId(id);
		f.setName(name);
		ResponseEntity<FreelancerVO> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(f);
		return response;
	}
	
	@PostMapping("/resume/{id}")
	public ResponseEntity<ResumeVO> uploadResume(@RequestParam MultipartFile resume, @PathVariable String id) throws ServiceException {
		String md5 = resumeService.importResume(resume, id);
		ResumeVO f = new ResumeVO();
		f.setCheckSum(md5);
		f.setSize(resume.getSize());
		ResponseEntity<ResumeVO> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(f);
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
