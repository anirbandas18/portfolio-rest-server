package com.teenthofabud.portfolio.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.vo.ApiResponse;
import com.teenthofabud.portfolio.vo.FreelancerVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/freelancer")
@Api(consumes = "application/json", produces = "application/json", protocols = "http", description = "REST to CRUD operation mapping on freelancer properties and read/write for freelancer avatar and resume files", tags = {
		"Freelancer" })
public class FreelancerController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FreelancerController.class);

	@Autowired
	private FreelancerService freelancerService;

	@Value("${resume.base.location}")
	private String resumeBaseLocation;

	@Value("${avatar.base.location}")
	private String avatarBaseLocation;

	@ApiOperation(value = "read freelancer details", response = Freelancer.class, produces = "application/json", notes = "Read details of freelancer from database as identified by its ID and expose it")
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
					throws HttpStatusCodeException{
		Freelancer freelancer = freelancerService.read(id);
		ResponseEntity<Freelancer> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).lastModified(System.currentTimeMillis())
				.body(freelancer);
		return response;
	}

	@ApiOperation(value = "delete freelancer details", response = ApiResponse.class, produces = "application/json", notes = "Delete details of freelancer from database as identified by its ID and return the operation status")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) 
					throws HttpStatusCodeException {
		Boolean changed = freelancerService.delete(id);
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ApiResponse body = new ApiResponse();
		body.setStatus("deleted");
		body.setMessage(String.valueOf(changed));
		ResponseEntity<ApiResponse> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
		return response;
	}

	@ApiOperation(value = "create freelancer details", response = ApiResponse.class, produces = "application/json", notes = "Create freelancer in database with corresponding data passed as JSON in request body and return the ID after successful operation")
	@PostMapping
	public ResponseEntity<ApiResponse> postFreelancerDetails(
			@ApiParam(value = "freelancer details", required = true) @Valid @RequestBody FreelancerVO vo)
			throws HttpStatusCodeException {
		Freelancer model = new Freelancer();
		model.setDetail(vo.getDetail());
		model.setLanguagesKnown(vo.getLanguagesKnown());
		model.setLocation(vo.getLocation());
		model.setProfile(vo.getProfile());
		model.setSocialLinks(vo.getSocialLinks());
		String id = freelancerService.create(model);
		ApiResponse success = new ApiResponse();
		success.setStatus("id");
		success.setMessage(id);
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ResponseEntity<ApiResponse> response = new ResponseEntity<>(success, HttpStatus.CREATED);
		return response;
	}

	@ApiOperation(value = "update freelancer details", response = ApiResponse.class, produces = "application/json", notes = "Update details of freelancer wrt ID in database with corresponding data passed as JSON in request body")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> putFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id,
			@ApiParam(value = "freelancer details", required = true) @Valid @RequestBody FreelancerVO vo)
			throws HttpStatusCodeException {
		Freelancer model = new Freelancer();
		model.setId(id);
		model.setDetail(vo.getDetail());
		model.setLanguagesKnown(vo.getLanguagesKnown());
		model.setLocation(vo.getLocation());
		model.setProfile(vo.getProfile());
		model.setSocialLinks(vo.getSocialLinks());
		Boolean changed = freelancerService.update(id, model);
		ApiResponse body = new ApiResponse();
		body.setStatus("updated");
		body.setMessage(String.valueOf(changed));
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setDate(System.currentTimeMillis());*/
		ResponseEntity<ApiResponse> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
		return response;
	}

	@ApiOperation(value = "upload freelancer's resume file", response = ApiResponse.class, produces = "application/json", notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/resume/{id}")
	public ResponseEntity<ApiResponse> uploadResume(
			@ApiParam(value = "freelancer's resume file", required = true) @RequestParam MultipartFile resume,
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) 
					throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(resumeBaseLocation);
		dto.setId(id);
		dto.setFile(resume);
		dto.setType(FreelancerFile.RESUME);
		try {
			Boolean status = freelancerService.importFile(dto);
			ApiResponse body = new ApiResponse();
			body.setStatus("uploaded");
			body.setMessage(String.valueOf(status));
			/*HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setDate(System.currentTimeMillis());*/
			ResponseEntity<ApiResponse> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
			return response;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
	}

	@ApiOperation(value = "download freelancer's resume file", response = ByteArrayResource.class, notes = "Download resume file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
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
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
	}

	@ApiOperation(value = "upload freelancer's avatar file", response = ApiResponse.class, produces = "application/json", notes = "Upload avatar file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/avatar/{id}")
	public ResponseEntity<ApiResponse> uploadAvatar(
			@ApiParam(value = "freelancer's avatar file", required = true) @RequestParam MultipartFile avatar,
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) 
					throws HttpStatusCodeException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(avatarBaseLocation);
		dto.setId(id);
		dto.setFile(avatar);
		dto.setType(FreelancerFile.AVATAR);
		try {
			Boolean status = freelancerService.importFile(dto);
			ApiResponse body = new ApiResponse();
			body.setStatus("updated");
			body.setMessage(String.valueOf(status));
			/*HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setDate(System.currentTimeMillis());*/
			ResponseEntity<ApiResponse> response = new ResponseEntity<>(body, HttpStatus.IM_USED);
			return response;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
	}

	@ApiOperation(value = "download freelancer's avatar file", response = ByteArrayResource.class, notes = "Download avatar file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/avatar/{id}")
	public ResponseEntity<Resource> downloadAvatar(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
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
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
	}

}
