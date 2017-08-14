package com.teenthofabud.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.ResumeService;
import com.teenthofabud.portfolio.vo.FreelancerVO;
import com.teenthofabud.portfolio.vo.ResumeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/freelancer")
@Api(consumes = "application/json", produces = "application/json", protocols = "http", tags = {
		"Freelancer" }, description = "REST to CRUD mapping on freelancer and it's resume")
public class FreelancerController {

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private FreelancerService freelancerService;

	@ApiOperation(value = "read freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Read details of freelancer from database as identified by its ID and expose it ")
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		Freelancer freelancer = freelancerService.readFreelancer(id);
		ResponseEntity<Freelancer> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body(freelancer);
		return response;
	}

	@ApiOperation(value = "delete freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Delete details of freelancer from database as identified by its ID and expose the operation status ")
	@DeleteMapping("/{id}")
	public ResponseEntity<FreelancerVO> deleteFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		Boolean changed = freelancerService.deleteFreelancer(id);
		FreelancerVO f = new FreelancerVO();
		f.setId(id);
		f.setChanged(changed);
		ResponseEntity<FreelancerVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(f);
		return response;
	}
	
	@ApiOperation(value = "create freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Create details of freelancer in database with corresponding data passed as JSON in request body ")
	@PostMapping
	public ResponseEntity<FreelancerVO> postFreelancerDetails(
			@ApiParam(value = "freelancer details", required = true) @RequestBody Freelancer freelancer)
			throws ServiceException {
		String id = freelancerService.createFreelancer(freelancer);
		String name = freelancer.getFirstName() + " " + freelancer.getLastName();
		FreelancerVO f = new FreelancerVO();
		f.setId(id);
		f.setName(name);
		ResponseEntity<FreelancerVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(f);
		return response;
	}

	@ApiOperation(value = "update freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Update details of freelancer in database with corresponding data passed as JSON in request body ")
	@PutMapping
	public ResponseEntity<FreelancerVO> putFreelancerDetails(
			@ApiParam(value = "freelancer details", required = true) @RequestBody Freelancer freelancer)
			throws ServiceException {
		Boolean changed = freelancerService.updateFreelancer(freelancer);
		FreelancerVO f = new FreelancerVO();
		f.setId(freelancer.getId());
		f.setChanged(changed);
		ResponseEntity<FreelancerVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(f);
		return response;
	}

	@ApiOperation(value = "upload freelancer resume file", response = Freelancer.class, produces = "application/json", 
			notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Respond with checksum generated for the file alongwith it's size in bytes")
	@PostMapping("/resume/{id}")
	public ResponseEntity<ResumeVO> uploadResume(
			@ApiParam(value = "freelancer's resume file", required = true) @RequestParam MultipartFile resume, 
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
			throws ServiceException {
		String md5 = resumeService.importResume(resume, id);
		ResumeVO f = new ResumeVO();
		f.setCheckSum(md5);
		f.setSize(resume.getSize());
		ResponseEntity<ResumeVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(f);
		return response;
	}
	
	@ApiOperation(value = "download freelancer resume file", response = Freelancer.class, produces = "application/json", 
			notes = "Download resume file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		byte[] resume = resumeService.exportResume(id);
		Resource resource = new ByteArrayResource(resume);
		ResponseEntity<Resource> response = ResponseEntity.ok().contentLength(resume.length)
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE)).body(resource);
		return response;
	}

}
