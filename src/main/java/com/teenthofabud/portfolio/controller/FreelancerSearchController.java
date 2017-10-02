package com.teenthofabud.portfolio.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.teenthofabud.portfolio.core.constants.SortOrder;
import com.teenthofabud.portfolio.core.exception.EmptySearchParametersException;
import com.teenthofabud.portfolio.core.exception.InvalidSearchParametersException;
import com.teenthofabud.portfolio.core.validation.groups.RequestParamValidation;
import com.teenthofabud.portfolio.core.validation.payloads.FreelancerFields;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.UtilityService;
import com.teenthofabud.portfolio.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Validated
@RestController
@RequestMapping("/freelancer/search")
@Api(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http", 
	description = "end points for searching freelancer entities based on values of specified properties for the same", 
	tags = { "FreelancerSearch" })
public class FreelancerSearchController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FreelancerSearchController.class);
	
	@Autowired
	private FreelancerService freelancerService;
	@Autowired
	private UtilityService util;
	@Autowired
	private Validator validator;
	@Autowired
	private Sort asc;
	@Autowired
	private Sort desc;
	
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer found matching criteria", response = FreelancerFields.class),
			@ApiResponse(code = 400, message = "Freelancer search parameters validations failed with error", response = ResponseVO.class),
			@ApiResponse(code = 404, message = "Freelancer not found matching the search criteria", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer search parameters are invalid", response = ResponseVO.class) })
	@ApiOperation(value = "read details of a single freelancer matching the criteria", response = FreelancerFields.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read details of freelancer from database as identified by values of it's properties and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerSearch")
	@GetMapping("/single")
	public ResponseEntity<Freelancer> getSingleFreelancer(
			@ApiParam(name = "firstName", value = "freelancer's first name", required = false) @RequestParam(required = false) String firstName,
			@ApiParam(name = "lastName", value = "freelancer's last name", required = false) @RequestParam(required = false) String lastName,
			@ApiParam(name = "phoneNumber", value = "freelancer's phone number", required = true) @RequestParam(required = false) String phoneNumber,
			@ApiParam(name = "emailId", value = "freelancer's email id", required = false) @RequestParam(required = false) String emailId,
			@ApiParam(name = "requestParameters", value = "aggregates all request query parameters to map", hidden = true) @RequestParam Map<String,String> requestParameters)
			throws HttpStatusCodeException, ConstraintViolationException {
		LOG.info("Search parameters: {}", requestParameters.toString());
		// map request param string values to respective data type
		Map<String,Object> searchParameters = requestParamsToDetailPOJOMap(requestParameters);
		// convert map to corresponding pojo
		Detail freelancerDetails = (Detail) util.map2POJO(searchParameters, Detail.class);
		Set<ConstraintViolation<Detail>> violations = validator.validate(freelancerDetails, RequestParamValidation.class);
		if(violations.isEmpty()) {
			Freelancer freelancer = freelancerService.read(freelancerDetails);
			LOG.info("Search successful");
			ResponseEntity<Freelancer> response = ResponseEntity.ok().body(freelancer);
			return response;
		} else {
			LOG.error("Error validating freelancer details. No. of violations: {}", violations.size());
			throw new InvalidSearchParametersException(Detail.class, violations);
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancers found matching criteria", response = FreelancerFields.class),
			@ApiResponse(code = 404, message = "Freelancer not found matching the search criteria", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer search parameters are invalid", response = ResponseVO.class) })
	@ApiOperation(value = "read multiple freelancer details matching criteria", responseContainer = "List", response = FreelancerFields.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read all freelancers from database as identified by values of properties and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerSearch")
	@GetMapping("/multiple/{order}")
	public ResponseEntity<?> getMultipleFreelancers(
			@ApiParam(name = "firstName", value = "freelancer's first name", required = false) @RequestParam(required = false) String firstName,
			@ApiParam(name = "lastName", value = "freelancer's last name", required = false) @RequestParam(required = false) String lastName,
			@ApiParam(name = "phoneNumber", value = "freelancer's phone number", required = true) @RequestParam(required = false) String phoneNumber,
			@ApiParam(name = "emailId", value = "freelancer's email id", required = false) @RequestParam(required = false) String emailId,
			@ApiParam(value = "aggregates all request query parameters to map", access = "internal", hidden = true) @RequestParam Map<String,String> requestParameters,
			@ApiParam(name = "order", value = "search result order", required = false, allowableValues = "asc,desc") @PathVariable(required = false) String sortOrder)
			throws HttpStatusCodeException, ConstraintViolationException {
		LOG.info("Search parameters: {}", requestParameters.toString());
		List<Freelancer> matchingFreelancers = new ArrayList<>();
		Sort[] order = parseSortOrder(sortOrder);
		try {
			Map<String,Object> searchParameters = requestParamsToDetailPOJOMap(requestParameters);
			Detail freelancerDetails = (Detail) util.map2POJO(searchParameters, Detail.class);
			Set<ConstraintViolation<Detail>> violations = validator.validate(freelancerDetails, RequestParamValidation.class);
			if(violations.isEmpty()) {
				LOG.info("Fetching search results of all freelancers in order: {}", order[0]);
				matchingFreelancers = freelancerService.readAll(freelancerDetails, order);
			} else {
				LOG.error("Error validating freelancer details. No. of violations: {}", violations.size());
				throw new InvalidSearchParametersException(Detail.class, violations);
			}
		} catch (EmptySearchParametersException e) {
			LOG.info("Fetching all freelancers since search parameters are not provided");
			matchingFreelancers = freelancerService.readAll(order);
		}
		ResponseEntity<?> response = ResponseEntity.ok().body(matchingFreelancers);
		LOG.info("Search successful");
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All freelancers", response = FreelancerFields.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "No freelancers found", response = ResponseVO.class) })
	@ApiOperation(value = "read all freelancer details", responseContainer = "List", response = FreelancerFields.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read all freelancers from database and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") }, tags = "FreelancerSearch")
	@GetMapping("/all/{order}")
	public ResponseEntity<List<Freelancer>> getAllFreelancers(
			@ApiParam(name = "order", value = "search result order", required = false, allowableValues = "asc,desc") @PathVariable(required = false) String sortOrder)
			throws HttpStatusCodeException {
		Sort[] order = parseSortOrder(sortOrder);
		LOG.info("Fetching search results of all freelancers in order: {}", order[0]);
		List<Freelancer> matchingFreelancers = freelancerService.readAll(order);
		ResponseEntity<List<Freelancer>> response = ResponseEntity.ok().body(matchingFreelancers);
		LOG.info("Search successful");
		return response;
	}

	private Map<String,Object> requestParamsToDetailPOJOMap(Map<String,String> source) throws EmptySearchParametersException {
		if(source.isEmpty()) {
			LOG.error("No search parameters provided");
			throw new EmptySearchParametersException();
		} else {
			Map<String,Object> target = new LinkedHashMap<>();
			for(String key : source.keySet()) {
				String value = source.get(key);
				target.put(key, value);
			}
			LOG.info("Search parameters parsed to respective primitve types");
			return target;
		}
	}

	private Sort[] parseSortOrder(String sortOrder) {
		List<Sort> order = new ArrayList<>();
		if (StringUtils.hasText(sortOrder)) {
			SortOrder x = SortOrder.valueOf(sortOrder);
			order.add(x == SortOrder.ASC ? asc : desc);
		} else {
			LOG.info("Defaulting to ASC search order since no search order is provided");
			order.add(asc);
		}
		return order.toArray(new Sort[1]);
	}
}
