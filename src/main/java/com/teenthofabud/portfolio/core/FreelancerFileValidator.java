package com.teenthofabud.portfolio.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.MediaType;
import com.teenthofabud.portfolio.core.constants.FreelancerFile;
import com.teenthofabud.portfolio.core.validation.constraints.CheckFreelancerFile;


public class FreelancerFileValidator implements ConstraintValidator<CheckFreelancerFile, MultipartFile> {

	private FreelancerFile type;

	private List<String> resumeMimes;
	private List<String> avatarMimes;

	@Override
	public void initialize(CheckFreelancerFile constraintAnnotation) {
		// TODO Auto-generated method stub
		this.type = constraintAnnotation.type();
		this.resumeMimes = createSortedMediaTypes(Arrays.asList(MediaType.PDF, MediaType.MICROSOFT_WORD, MediaType.OOXML_DOCUMENT, MediaType.OPENDOCUMENT_TEXT));
		this.avatarMimes = createSortedMediaTypes(Arrays.asList(MediaType.JPEG, MediaType.PNG, MediaType.TIFF));
	}

	private Comparator<MediaType> mediaTypeComparator = new Comparator<MediaType>() {

		@Override
		public int compare(MediaType o1, MediaType o2) {
			// TODO Auto-generated method stub
			return o1.toString().compareTo(o2.toString());
		}
		
	};
	
	private List<String> createSortedMediaTypes(List<MediaType> mediaTypes) {
		// TODO Auto-generated method stub
		Collections.sort(mediaTypes, mediaTypeComparator);
		List<String> mimes = mediaTypes.stream().map(mt -> mt.toString()).collect(Collectors.toList());
		return mimes;
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
				int indexAvatar = Collections.binarySearch(avatarMimes, contentType);
				isValid = indexAvatar >= 0;
				break;
			case RESUME:
				int indexResume = Collections.binarySearch(resumeMimes, contentType);
				isValid = indexResume >= 0;
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