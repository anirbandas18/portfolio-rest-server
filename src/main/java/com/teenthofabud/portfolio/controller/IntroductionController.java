package com.teenthofabud.portfolio.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teenthofabud.portfolio.vo.IntroductionVO;

@RestController
@RequestMapping("/intro")
public class IntroductionController {
	
	@GetMapping("/{id}")
	public ResponseEntity<IntroductionVO> getFreelancerIntroduction(@PathVariable String id) {
		IntroductionVO body = null;
		ResponseEntity<IntroductionVO> response = new ResponseEntity<IntroductionVO>(body, HttpStatus.OK);
		return response;
	}
	
	@Value("${pdf.location}")
	private String pdfLocation;
	
	@Value("${pdf.name}")
	private String pdfName;
	
	@GetMapping("/resume/download")
	public void downloadResume(HttpServletResponse response) throws IOException {
		Path resumePath = Paths.get(pdfLocation, pdfName);
		OutputStream out = response.getOutputStream();
		Files.copy(resumePath, out);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + pdfName);
		response.flushBuffer();
	}
	
}
