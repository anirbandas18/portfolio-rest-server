package com.teenthofabud.portfolio.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.teenthofabud.portfolio.core.validation.groups.RequestBodyValidation;
import com.teenthofabud.portfolio.core.validation.payloads.FreelancerFields;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.vo.FreelancerVO;
import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Validated
@RestController
@RequestMapping("/freelancer")
@Api(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http", 
	description = "end points for managing freelancer entity through CRUD operations", 
	tags = { "FreelancerManagement" })
public class FreelancerManagementController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FreelancerManagementController.class);
	
	@Autowired
	private FreelancerService freelancerService;
	
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer found", response = FreelancerFields.class),
			@ApiResponse(code = 404, message = "No freelancer found with ID", response = ResponseVO.class) })
	@ApiOperation(value = "read freelancer details", response = FreelancerFields.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read details of freelancer from database as identified by its ID and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerManagement")
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) 
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		Freelancer freelancer = freelancerService.read(id);
		ResponseEntity<Freelancer> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.lastModified(System.currentTimeMillis()).body(freelancer);
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "Freelancer deleted", response = ResponseVO.class),
			@ApiResponse(code = 404, message = "No freelancer found with ID", response = ResponseVO.class) })
	@ApiOperation(value = "delete freelancer details", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Delete details of freelancer from database as identified by its ID and return the operation status", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerManagement")
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseVO> deleteFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true)  
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id)
			throws HttpStatusCodeException {
		Boolean changed = freelancerService.delete(id);
		ResponseVO body = new ResponseVO();
		body.setStatus("deleted");
		body.setMessage(String.valueOf(changed));
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Freelancer created", response = ResponseVO.class),
			@ApiResponse(code = 409, message = "Freelancer already exists with phone number/email id", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer field validations failed with error", response = ValidationVO.class) })
	@ApiOperation(value = "create freelancer details", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Create freelancer in database with corresponding data passed as JSON in request body and return the ID after successful operation", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerManagement")
	@PostMapping
	public ResponseEntity<ResponseVO> postFreelancerDetails(
			@ApiParam(name = "freelancerData", value = "freelancer model data", required = true) @Validated(RequestBodyValidation.class) 
			@RequestBody FreelancerVO freelancerData)
			throws HttpStatusCodeException {
		LOG.info("Freelancer data recieved: {}", freelancerData);
		Freelancer model = new Freelancer();
		model.setDetail(freelancerData.getDetail());
		model.setLanguagesKnown(freelancerData.getLanguagesKnown());
		model.setLocation(freelancerData.getLocation());
		model.setProfile(freelancerData.getProfile());
		model.setSocialLinks(freelancerData.getSocialLinks());
		String id = freelancerService.create(model);
		ResponseVO success = new ResponseVO();
		success.setStatus("id");
		success.setMessage(id);
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(success, HttpStatus.CREATED);
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Freelancer created", response = ResponseVO.class),
			@ApiResponse(code = 409, message = "Freelancer already exists with phone number/email id", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer field validations failed with error", response = ValidationVO.class) })
	@ApiOperation(value = "update freelancer details", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Update details of freelancer wrt ID in database with corresponding data passed as JSON in request body", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerManagement")
	@PutMapping("/{id}")
	public ResponseEntity<ResponseVO> putFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) 
			@NotNull(message = "freelancer id can't be null", payload = FreelancerFields.id.class) 
			@Pattern(message = "freelancer id has to be a positive whole number", regexp = "[0-9]+", payload = FreelancerFields.id.class) 
			@PathVariable String id,
			@ApiParam(name = "freelancerData", value = "freelancer entity data", required = true) @Validated(RequestBodyValidation.class)
			@RequestBody FreelancerVO freelancerData)
			throws HttpStatusCodeException {
		LOG.info("Freelancer data recieved: {} for ID: {}", freelancerData, id);
		Freelancer model = new Freelancer();
		model.setId(id);
		model.setDetail(freelancerData.getDetail());
		model.setLanguagesKnown(freelancerData.getLanguagesKnown());
		model.setLocation(freelancerData.getLocation());
		model.setProfile(freelancerData.getProfile());
		model.setSocialLinks(freelancerData.getSocialLinks());
		Boolean changed = freelancerService.update(id, model);
		ResponseVO body = new ResponseVO();
		body.setStatus("updated");
		body.setMessage(String.valueOf(changed));
		ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
		return response;
	}

}
