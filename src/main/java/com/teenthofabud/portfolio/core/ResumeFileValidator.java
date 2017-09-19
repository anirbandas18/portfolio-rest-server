package com.teenthofabud.portfolio.core;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ResumeFileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MultipartFile.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		MultipartFile file = (MultipartFile) target;
		String contentType = file.getContentType(); 
		if(!contentType.equals(MediaType.APPLICATION_PDF_VALUE)) {
			errors.reject("contentType", "Unaccepted resume file content type: " + contentType);
		} else if(file.isEmpty()) {
			errors.reject("emptyFile", "Empty resume file");
		}
	}

}
