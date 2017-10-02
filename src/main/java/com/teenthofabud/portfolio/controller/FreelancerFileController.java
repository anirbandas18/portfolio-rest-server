package com.teenthofabud.portfolio.controller;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.validation.constraints.CheckFreelancerFile;
import com.teenthofabud.portfolio.core.validation.payloads.FreelancerFields;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@RestController
@RequestMapping("/freelancer/file")
@Api(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http", 
	description = "end points for managing freelancer's file based on the entity id for the same", 
	tags = { "FreelancerFile" })
public class FreelancerFileController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FreelancerFileController.class);
	
	@Autowired
	private FreelancerService freelancerService;
	
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "Freelancer resume file uploaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error saving freelancer resume file", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer resume file is invalid", response = ValidationVO.class) })
	@ApiOperation(value = "upload freelancer's resume file", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status", tags = "FreelancerFile")
	@PutMapping("/resume/{id}")
	public ResponseEntity<ResponseVO> uploadResume(
			@ApiParam(name = "resume", value = "freelancer's resume file", required = true, allowEmptyValue = false) @RequestParam 
			@CheckFreelancerFile(type = FreelancerFile.RESUME, payload = FreelancerFields.resumeFile.class) MultipartFile resume,
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		try {
			FreelancerFileDTO dto = new FreelancerFileDTO();
			//dto.setBaseFileLocation(fileBaseLocation);
			dto.setId(id);
			dto.setType(FreelancerFile.RESUME);
			Boolean status = freelancerService.importFile(dto);
			ResponseVO body = new ResponseVO();
			body.setStatus("uploaded");
			body.setMessage(String.valueOf(status));
			ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
			return response;
		} catch (IOException e) {
			LOG.error("Error saving freelancer file:{} ", e);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer resume file downloaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error fetching freelancer resume file", response = ResponseVO.class) })
	@ApiOperation(value = "download freelancer's resume file", response = ByteArrayResource.class, notes = "Download resume file of freelancer as identified by its respective ID from the file system", tags = "FreelancerFile")
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) 
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		try {
			FreelancerFileDTO dto = freelancerService.exportFile(id, FreelancerFile.RESUME);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", dto.getFile().getName());
			headers.setContentLength(dto.getFile().getSize());
			headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
			headers.setDate(System.currentTimeMillis());
			Resource resource = new ByteArrayResource(dto.getFile().getBytes());
			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).body(resource);
			return response;
		} catch (IOException e) {
			LOG.error("Error fetching freelancer file:{} ", e);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "Freelancer avatar file uploaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error saving freelancer avatar file", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer avatar file is invalid", response = ValidationVO.class) })
	@ApiOperation(value = "upload freelancer's avatar file", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Upload avatar file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status", tags = "FreelancerFile")
	@PutMapping("/avatar/{id}")
	public ResponseEntity<ResponseVO> uploadAvatar(
			@ApiParam(name = "avatar", value = "freelancer's avatar file", required = true, allowEmptyValue = false) @RequestParam 
			@CheckFreelancerFile(type = FreelancerFile.AVATAR, payload = FreelancerFields.avatarFile.class) MultipartFile avatar,
			@ApiParam(name = "id", value = "freelancer unique id", required = true)
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		//dto.setBaseFileLocation(fileBaseLocation);
		dto.setId(id);
		dto.setFile(avatar);
		dto.setType(FreelancerFile.AVATAR);
		try {
			Boolean status = freelancerService.importFile(dto);
			ResponseVO body = new ResponseVO();
			body.setStatus("updated");
			body.setMessage(String.valueOf(status));
			ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
			return response;
		} catch (IOException e) {
			LOG.error("Error saving freelancer file:{} ", e);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer avatar file downloaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error fetching freelancer avatar file", response = ResponseVO.class) })
	@ApiOperation(value = "download freelancer's avatar file", response = ByteArrayResource.class, notes = "Download avatar file of freelancer as identified by its respective ID from the file system", tags = "FreelancerFile")
	@GetMapping("/avatar/{id}")
	public ResponseEntity<Resource> downloadAvatar(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) 
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		try {
			FreelancerFileDTO dto = freelancerService.exportFile(id, FreelancerFile.AVATAR);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", dto.getFile().getName());
			headers.setContentLength(dto.getFile().getSize());
			headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
			headers.setDate(System.currentTimeMillis());
			Resource resource = new ByteArrayResource(dto.getFile().getBytes());
			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).body(resource);
			return response;
		} catch (IOException e) {
			LOG.error("Error fetching freelancer file:{} ", e);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
