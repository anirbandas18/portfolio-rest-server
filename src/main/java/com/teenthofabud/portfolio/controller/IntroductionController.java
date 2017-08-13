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

import com.teenthofabud.portfolio.exception.ResumeException;
import com.teenthofabud.portfolio.service.ResumeService;
import com.teenthofabud.portfolio.vo.IntroductionVO;

@RestController
@RequestMapping("/introduction")
public class IntroductionController {
	
	@Autowired
	private ResumeService resumeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<IntroductionVO> getFreelancerIntroduction(@PathVariable String id) {
		IntroductionVO body = null;
		ResponseEntity<IntroductionVO> response = new ResponseEntity<IntroductionVO>(body, HttpStatus.OK);
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
		} catch (ResumeException e) {
			Resource resource = new DescriptiveResource(e.getMessage());
			response = new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
}
