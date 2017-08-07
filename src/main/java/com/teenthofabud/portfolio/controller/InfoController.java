package com.teenthofabud.portfolio.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
	
	@Value("${resume.location}")
	private String resumeLocation;
	
	@GetMapping("/download/resume")
	public void downloadResume(HttpServletResponse response) throws IOException {
		Path resumePath = Paths.get(resumeLocation);
		String pdfName = resumePath.getFileName().toString();
		OutputStream out = response.getOutputStream();
		Files.copy(resumePath, out);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + pdfName);
		response.flushBuffer();
	}

}
