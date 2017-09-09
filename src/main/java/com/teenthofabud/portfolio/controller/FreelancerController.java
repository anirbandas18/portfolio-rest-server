package com.teenthofabud.portfolio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.constants.SortOrder;
import com.teenthofabud.portfolio.core.exception.EmptySearchParametersException;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.service.UtilityService;
import com.teenthofabud.portfolio.vo.FreelancerVO;
import com.teenthofabud.portfolio.vo.ResponseVO;
import com.teenthofabud.portfolio.vo.ValidationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("/freelancer")
@Api(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http", description = "REST to CRUD operation mapping on freelancer properties and read/write for freelancer avatar and resume files", tags = {
		"Freelancer" })
public class FreelancerController {

	private static final Logger LOG = LoggerFactory.getLogger(FreelancerController.class);

	@Autowired
	private FreelancerService freelancerService;
	@Autowired
	private UtilityService util;
	@Autowired
	private Sort asc;
	@Autowired
	private Sort desc;
	@Value("${resume.base.location}")
	private String resumeBaseLocation;
	@Value("${avatar.base.location}")
	private String avatarBaseLocation;
	
	/*@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer found matching criteria", response = Freelancer.class),
			@ApiResponse(code = 422, message = "Freelancer search criteria validations failed with error", response = ValidationVO.class),
			@ApiResponse(code = 404, message = "Freelancer not found matching the criteria", response = ResponseVO.class) })
	@ApiOperation(value = "read single freelancer details matching criteria", response = Freelancer.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read details of freelancer from database as identified by values of it's properties and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@GetMapping("/search/single")
	public ResponseEntity<ResponseVO> getSingleFreelancer(
			@RequestParam Map<String,String> params,
			@ApiParam(value = "freelancer search criterias", required = true, name = "id") @RequestParam Integer id,
@ApiParam(value = "freelancer search criterias", required = true, name = "name") @RequestParam String name)
			throws HttpStatusCodeException {
		LOG.info(params.toString());
		ResponseVO freelancer = new ResponseVO("parsed", params.toString());
		ResponseEntity<ResponseVO> response = ResponseEntity.ok().body(freelancer);
		return response;
	}*/

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer found matching criteria", response = Freelancer.class),
			@ApiResponse(code = 400, message = "Freelancer search parameters validations failed with error", response = ResponseVO.class),
			@ApiResponse(code = 404, message = "Freelancer not found matching the search criteria", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer search parameters are invalid", response = ResponseVO.class) })
	@ApiOperation(value = "read details of a single freelancer matching the criteria", response = Freelancer.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read details of freelancer from database as identified by values of it's properties and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@GetMapping("/search/single")
	public ResponseEntity<Freelancer> getSingleFreelancer(
			@ApiParam(name = "firstName", value = "freelancer's first name", required = false) @RequestParam(required = false) String firstName,
			@ApiParam(name = "lastName", value = "freelancer's last name", required = false) @RequestParam(required = false) String lastName,
			@ApiParam(name = "phoneNumber", value = "freelancer's phone number", required = true) @RequestParam(required = true) String phoneNumber,
			@ApiParam(name = "emailId", value = "freelancer's email id", required = false) @RequestParam(required = false) String emailId,
			@ApiParam(value = "aggregates all request query parameters to map", access = "internal", hidden = true) @RequestParam Map<String,String> requestParameters)
			throws HttpStatusCodeException {
		LOG.info("Search parameters: {}", requestParameters.toString());
		Map<String,Object> searchParameters = requestParamsToDetailPOJOMap(requestParameters);
		Detail freelancerDetails = (Detail) util.map2POJO(searchParameters, Detail.class);
		Freelancer freelancer = freelancerService.read(freelancerDetails);
		LOG.info("Search successful");
		ResponseEntity<Freelancer> response = ResponseEntity.ok().body(freelancer);
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancers found matching criteria", response = Freelancer.class),
			@ApiResponse(code = 404, message = "Freelancer not found matching the search criteria", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer search parameters are invalid", response = ResponseVO.class) })
	@ApiOperation(value = "read multiple freelancer details matching criteria", responseContainer = "List", response = Freelancer.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read all freelancers from database as identified by values of properties and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@GetMapping("/search/multiple/{order}")
	public ResponseEntity<?> getMultipleFreelancers(
			@ApiParam(name = "firstName", value = "freelancer's first name", required = false) @RequestParam(required = false) String firstName,
			@ApiParam(name = "lastName", value = "freelancer's last name", required = false) @RequestParam(required = false) String lastName,
			@ApiParam(name = "phoneNumber", value = "freelancer's phone number", required = true) @RequestParam(required = true) String phoneNumber,
			@ApiParam(name = "emailId", value = "freelancer's email id", required = false) @RequestParam(required = false) String emailId,
			@ApiParam(value = "aggregates all request query parameters to map", access = "internal", hidden = true) @RequestParam Map<String,String> requestParameters,
			@ApiParam(name = "order", value = "search result order", required = false) @PathVariable String sortOrder)
			throws HttpStatusCodeException {
		LOG.info("Search parameters: {}", requestParameters.toString());
		List<Freelancer> matchingFreelancers = new ArrayList<>();
		Sort[] order = parseSortOrder(sortOrder);
		try {
			Map<String,Object> searchParameters = requestParamsToDetailPOJOMap(requestParameters);
			Detail freelancerDetails = (Detail) util.map2POJO(searchParameters, Detail.class);
			LOG.info("Fetching search results of all freelancers in order: {}", order[0]);
			matchingFreelancers = freelancerService.readAll(freelancerDetails, order);
		} catch (EmptySearchParametersException e) {
			LOG.info("Fetching all freelancers since search parameters are not provided");
			matchingFreelancers = freelancerService.readAll(order);
		}
		ResponseEntity<?> response = ResponseEntity.ok().body(matchingFreelancers);
		LOG.info("Search successful");
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All freelancers", response = Freelancer.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "No freelancers found", response = ResponseVO.class) })
	@ApiOperation(value = "read all freelancer details", responseContainer = "List", response = Freelancer.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read all freelancers from database and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@GetMapping("/all/{order}")
	public ResponseEntity<List<Freelancer>> getAllFreelancers(
			@ApiParam(name = "order", value = "search result order", required = false) @PathVariable String sortOrder)
			throws HttpStatusCodeException {
		Sort[] order = parseSortOrder(sortOrder);
		LOG.info("Fetching search results of all freelancers in order: {}", order[0]);
		List<Freelancer> matchingFreelancers = freelancerService.readAll(order);
		ResponseEntity<List<Freelancer>> response = ResponseEntity.ok().body(matchingFreelancers);
		LOG.info("Search successful");
		return response;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer found", response = Freelancer.class),
			@ApiResponse(code = 404, message = "No freelancer found with ID", response = ResponseVO.class) })
	@ApiOperation(value = "read freelancer details", response = Freelancer.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Read details of freelancer from database as identified by its ID and expose it", responseHeaders = {
			@ResponseHeader(name = "Content-Type", description = "Content type of response being returned by server viz., XML, JSON"),
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id)
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
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseVO> deleteFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true)  @PathVariable String id)
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
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@PostMapping
	public ResponseEntity<ResponseVO> postFreelancerDetails(
			@ApiParam(name = "freelancerData", value = "freelancer entity data", required = true) @Valid @RequestBody FreelancerVO freelancerData)
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
			@ResponseHeader(name = "Date", description = "Timestamp when the response gets created by the server") })
	@PutMapping("/{id}")
	public ResponseEntity<ResponseVO> putFreelancerDetails(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id,
			@ApiParam(name = "freelancerData", value = "freelancer entity data", required = true) @Valid @RequestBody FreelancerVO freelancerData)
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

	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "Freelancer resume file uploaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error saving freelancer resume file", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer resume file is invalid", response = ValidationVO.class) })
	@ApiOperation(value = "upload freelancer's resume file", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/resume/{id}")
	public ResponseEntity<ResponseVO> uploadResume(
			@ApiParam(name = "resume", value = "freelancer's resume file", required = true) @RequestParam MultipartFile resume,
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id)
			throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(resumeBaseLocation);
		dto.setId(id);
		dto.setFile(resume);
		dto.setType(FreelancerFile.RESUME);
		try {
			Boolean status = freelancerService.importFile(dto);
			ResponseVO body = new ResponseVO();
			body.setStatus("uploaded");
			body.setMessage(String.valueOf(status));
			ResponseEntity<ResponseVO> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
			return response;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer resume file downloaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error fetching freelancer resume file", response = ResponseVO.class) })
	@ApiOperation(value = "download freelancer's resume file", response = ByteArrayResource.class, notes = "Download resume file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id)
			throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setId(id);
		dto.setType(FreelancerFile.RESUME);
		try {
			dto = freelancerService.exportFile(dto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", dto.getFile().getName());
			headers.setContentLength(dto.getFile().getSize());
			headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
			headers.setDate(System.currentTimeMillis());
			Resource resource = new ByteArrayResource(dto.getFile().getBytes());
			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).body(resource);
			return response;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "Freelancer avatar file uploaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error saving freelancer avatar file", response = ResponseVO.class),
			@ApiResponse(code = 422, message = "Freelancer avatar file is invalid", response = ValidationVO.class) })
	@ApiOperation(value = "upload freelancer's avatar file", response = ResponseVO.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, notes = "Upload avatar file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/avatar/{id}")
	public ResponseEntity<ResponseVO> uploadAvatar(
			@ApiParam(name = "avatar", value = "freelancer's avatar file", required = true) @RequestParam MultipartFile avatar,
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id)
			throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(avatarBaseLocation);
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
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Freelancer avatar file downloaded", response = ResponseVO.class),
			@ApiResponse(code = 500, message = "Error fetching freelancer avatar file", response = ResponseVO.class) })
	@ApiOperation(value = "download freelancer's avatar file", response = ByteArrayResource.class, notes = "Download avatar file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/avatar/{id}")
	public ResponseEntity<Resource> downloadAvatar(
			@ApiParam(name = "id", value = "freelancer unique id", required = true) @PathVariable String id)
			throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setId(id);
		dto.setType(FreelancerFile.AVATAR);
		try {
			dto = freelancerService.exportFile(dto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", dto.getFile().getName());
			headers.setContentLength(dto.getFile().getSize());
			headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
			headers.setDate(System.currentTimeMillis());
			Resource resource = new ByteArrayResource(dto.getFile().getBytes());
			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).body(resource);
			return response;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
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
