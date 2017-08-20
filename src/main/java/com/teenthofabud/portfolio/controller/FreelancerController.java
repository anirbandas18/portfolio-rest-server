package com.teenthofabud.portfolio.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.configuration.constants.FreelancerFile;
import com.teenthofabud.portfolio.dto.FreelancerFileDTO;
import com.teenthofabud.portfolio.exception.ServiceException;
import com.teenthofabud.portfolio.exception.ValidationException;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.vo.FreelancerVO;
import com.teenthofabud.portfolio.vo.ResumeVO;
import com.teenthofabud.portfolio.vo.SuccessVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/freelancer")
@Api(consumes = "application/json", produces = "application/json", protocols = "http", description = "REST to CRUD operation mapping on freelancer properties and File READ/WRITE for freelancer avatar and resume files", tags = { "Freelancer" })
public class FreelancerController {

	@Autowired
	private FreelancerService freelancerService;
	
	@Value("${resume.base.location}")
	private String resumeBaseLocation;
	
	@Value("${avatar.base.location}")
	private String avatarBaseLocation;

	@ApiOperation(value = "read freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Read details of freelancer from database as identified by its ID and expose it")
	@GetMapping("/{id}")
	public ResponseEntity<Freelancer> getFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		Freelancer freelancer = freelancerService.readFreelancer(id);
		ResponseEntity<Freelancer> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(freelancer);
		return response;
	}

	@ApiOperation(value = "delete freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Delete details of freelancer from database as identified by its ID and return the operation status")
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessVO> deleteFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException {
		Boolean changed = freelancerService.deleteFreelancer(id);
		SuccessVO success = new SuccessVO(String.valueOf(changed));
		ResponseEntity<SuccessVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(success);
		return response;
	}
	
	@ApiOperation(value = "create freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Create freelancer in database with corresponding data passed as JSON in request body and return the ID after successful operation")
	@PostMapping
	public ResponseEntity<SuccessVO> postFreelancerDetails(
			@ApiParam(value = "freelancer details", required = true) @RequestBody @Valid FreelancerVO vo,
			BindingResult result)
			throws ServiceException, ValidationException {
		if(result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			throw new ValidationException(errors);
		} else {
			Freelancer model = new Freelancer();
			model.setDetail(vo.getDetail());
			model.setLanguagesKnown(vo.getLanguagesKnown());
			model.setLocation(vo.getLocation());
			model.setProfile(vo.getProfile());
			model.setSocialLinks(vo.getSocialLinks());
			String id = freelancerService.createFreelancer(model);
			SuccessVO success = new SuccessVO(id);
			ResponseEntity<SuccessVO> response = ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(success);
			return response;
		}
	}

	@ApiOperation(value = "update freelancer details", response = Freelancer.class, produces = "application/json", 
			notes = "Update details of freelancer wrt ID in database with corresponding data passed as JSON in request body")
	@PutMapping("/{id}")
	public ResponseEntity<SuccessVO> putFreelancerDetails(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id,
			@ApiParam(value = "freelancer details", required = true) @RequestBody FreelancerVO vo)
			throws ServiceException {
		Freelancer model = new Freelancer();
		model.setId(id);
		model.setDetail(vo.getDetail());
		model.setLanguagesKnown(vo.getLanguagesKnown());
		model.setLocation(vo.getLocation());
		model.setProfile(vo.getProfile());
		model.setSocialLinks(vo.getSocialLinks());
		Boolean changed = freelancerService.updateFreelancer(id, model);
		SuccessVO success = new SuccessVO(String.valueOf(changed));
		ResponseEntity<SuccessVO> response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(success);
		return response;
	}
	
	@ApiOperation(value = "upload freelancer's resume file", response = ResumeVO.class, produces = "application/json", 
			notes = "Upload resume file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/resume/{id}")
	public ResponseEntity<SuccessVO> uploadResume(
			@ApiParam(value = "freelancer's resume file", required = true) @RequestParam MultipartFile resume, 
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
			throws ServiceException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(resumeBaseLocation);
		dto.setId(id);
		dto.setFile(resume);
		dto.setType(FreelancerFile.RESUME);
		Boolean status = freelancerService.importFile(dto);
		SuccessVO success = new SuccessVO(String.valueOf(status));
		ResponseEntity<SuccessVO> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(success);
		return response;
	}
	
	@ApiOperation(value = "download freelancer's resume file", response = ByteArrayResource.class, 
			notes = "Download resume file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/resume/{id}")
	public ResponseEntity<Resource> downloadResume(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException, IOException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setId(id);
		dto.setType(FreelancerFile.RESUME);
		dto = freelancerService.exportFile(dto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", dto.getFile().getName());
		headers.setContentLength(dto.getFile().getSize());
		headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
		Resource resource = new ByteArrayResource(dto.getFile().getBytes());
		ResponseEntity<Resource> response = ResponseEntity.ok()
				.headers(headers)
				.body(resource);
		return response;
	}
	
	@ApiOperation(value = "upload freelancer's avatar file", response = ResumeVO.class, produces = "application/json", 
			notes = "Upload avatar file of freelancer as identified by its respective ID and store on the file system. Override if already exists. Respond with operation status")
	@PutMapping("/avatar/{id}")
	public ResponseEntity<SuccessVO> uploadAvatar(
			@ApiParam(value = "freelancer's avatar file", required = true) @RequestParam MultipartFile avatar, 
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id)
			throws ServiceException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setBaseFileLocation(avatarBaseLocation);
		dto.setId(id);
		dto.setFile(avatar);
		dto.setType(FreelancerFile.AVATAR);
		Boolean status = freelancerService.importFile(dto);
		SuccessVO success = new SuccessVO(String.valueOf(status));
		ResponseEntity<SuccessVO> response = ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(success);
		return response;
	}
	
	@ApiOperation(value = "download freelancer's avatar file", response = ByteArrayResource.class, 
			notes = "Download avatar file of freelancer as identified by its respective ID from the file system")
	@GetMapping("/avatar/{id}")
	public ResponseEntity<Resource> downloadAvatar(
			@ApiParam(value = "freelancer ID", required = true) @PathVariable String id) throws ServiceException, IOException {
		FreelancerFileDTO dto = new FreelancerFileDTO();
		dto.setId(id);
		dto.setType(FreelancerFile.AVATAR);
		dto = freelancerService.exportFile(dto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", dto.getFile().getName());
		headers.setContentLength(dto.getFile().getSize());
		headers.setContentType(MediaType.valueOf(dto.getFile().getContentType()));
		Resource resource = new ByteArrayResource(dto.getFile().getBytes());
		ResponseEntity<Resource> response = ResponseEntity.ok()
				.headers(headers)
				.body(resource);
		return response;
	}
	
}
