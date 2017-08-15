package com.teenthofabud.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.ResumeService;
import com.teenthofabud.portfolio.vo.ResumeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/resume")
@Api(consumes = "application/json", produces = "application/json", protocols = "http", description = "REST endpoints for exchanging resume file of freelancer", tags = { "Resume" })
public class ResumeController {

	@Autowired
	private ResumeService resumeService;
	
	@ApiOperation(value = "upload freelancer's resume file", response = Freelancer.class, produces = "application/json", 
			notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Respond with checksum generated for the file alongwith it's size in bytes")
	@PostMapping("/{id}")
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
	
	@ApiOperation(value = "download freelancer's resume file", response = Freelancer.class, produces = "application/json", 
			notes = "Download resume file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		byte[] resume = resumeService.exportResume(id);
		Resource resource = new ByteArrayResource(resume);
		ResponseEntity<Resource> response = ResponseEntity.ok().contentLength(resume.length)
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE)).body(resource);
		return response;
	}

}