package com.teenthofabud.portfolio.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.google.common.net.MediaType;

@Configuration
public class ValidatorConfiguration {

	@Bean
	public LocalValidatorFactoryBean validatorFactory() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidator() {
		return new MethodValidationPostProcessor();
	}
	
	@Bean
	public List<MediaType> allowedResumeMimes() {
		List<MediaType> mimeList = new ArrayList<>();
		mimeList.add(MediaType.PDF);
		mimeList.add(MediaType.MICROSOFT_WORD);
		mimeList.add(MediaType.OOXML_DOCUMENT);
		mimeList.add(MediaType.OPENDOCUMENT_TEXT);
		return mimeList;
	}
	
	@Bean
	public List<MediaType> allowedAvatarMimes() {
		List<MediaType> mimeList = new ArrayList<>();
		mimeList.add(MediaType.JPEG);
		mimeList.add(MediaType.PNG);
		mimeList.add(MediaType.TIFF);
		return mimeList;
	}

}
