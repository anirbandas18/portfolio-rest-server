package com.teenthofabud.portfolio.core;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.MediaType;
import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.validation.constraints.CheckFreelancerFile;

@Component
public class FreelancerFileValidator implements ConstraintValidator<CheckFreelancerFile, MultipartFile> {

	private FreelancerFile type;

	@Resource
	private List<MediaType> allowedResumeMimes;
	@Resource
	private List<MediaType> allowedAvatarMimes;

	@Override
	public void initialize(CheckFreelancerFile constraintAnnotation) {
		// TODO Auto-generated method stub
		this.type = constraintAnnotation.type();
	}

	private Boolean isMimeTypePresent(List<MediaType> mimes, String contentType) {
		Boolean isPresent = false;
		for (MediaType mt : mimes) {
			if (mt.toString().equalsIgnoreCase(contentType)) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintContext) {
		// TODO Auto-generated method stub
		Boolean isValid = true;
		if (value == null || value.isEmpty() || value.getSize() == 0) {
			isValid = false;
		} else {
			String contentType = value.getContentType();
			switch (this.type) {
			case AVATAR:
				isValid = isMimeTypePresent(allowedAvatarMimes, contentType);
				break;
			case RESUME:
				isValid = isMimeTypePresent(allowedResumeMimes, contentType);
				break;
			}
			if (!isValid) {
				constraintContext.disableDefaultConstraintViolation();
				constraintContext.buildConstraintViolationWithTemplate("unprocessable file mime type: " + contentType)
				.addPropertyNode(type.name())
				.addConstraintViolation();
			}
		}
		return isValid;
	}

}