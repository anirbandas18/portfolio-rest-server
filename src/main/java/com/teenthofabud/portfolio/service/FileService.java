package com.teenthofabud.portfolio.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

	public byte[] exportFile(String baseLocation) throws IOException;
	
	public String importFile(MultipartFile resume, String baseLocation) throws IOException;
	
}
