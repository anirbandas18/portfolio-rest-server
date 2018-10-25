package com.teenthofabud.portfolio.controller;

import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teenthofabud.portfolio.core.security.CryptoService;
import com.teenthofabud.portfolio.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("/api/fm")
@Api(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http", 
description = "end point for cryptography and advanced path variable", 
tags = { "APIFileManager" })
public class APIFileManagerController {

	private static final Logger LOG = LoggerFactory.getLogger(APIFileManagerController.class);

	@Autowired
	private CryptoService crypto;

	@Value("${token.secret.key}")
	private String key;

	@ApiResponses(value = {@ApiResponse(code = 201, message = "crpytography performed", response = ResponseVO.class) })
	@ApiOperation(value = "produce token", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Reads file path and names as path variables", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "Crypto")
	@GetMapping(path = "/info/{destination:.*}/{fileName:.+}")
	public ResponseEntity<ResponseVO> fileInfo(@PathVariable String destination, @PathVariable String fileName) {
		ResponseVO body = new ResponseVO();
		LOG.info("destination: {} fileName: {}", destination, fileName);
		Random random = new SecureRandom();
		Integer min = 10000;
		Integer max = min * 10;
		Integer id = random.nextInt(max - min + 1) + min;
		String data = id.toString() + "." + String.valueOf(System.currentTimeMillis());
		String encrypted = crypto.encrypt(key, data);
		String decrypted = crypto.decrypt(key, encrypted);
		LOG.info("encrypted: {} decrypted: {}", encrypted, decrypted);
		body.setMessage(encrypted);
		body.setStatus(decrypted);
		return new ResponseEntity<ResponseVO>(body, HttpStatus.CREATED);
	}

}
