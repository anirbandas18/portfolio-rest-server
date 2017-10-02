package com.teenthofabud.portfolio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ApplicationBeanValidatorConfiguration {

	@Bean
	public LocalValidatorFactoryBean validatorFactory() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidator() {
		return new MethodValidationPostProcessor();
	}
	
	/*@Bean
	public List<String> resumeMimes() {
		List<MediaType> mediaTypes = Arrays.asList(MediaType.PDF, MediaType.MICROSOFT_WORD, MediaType.OOXML_DOCUMENT, MediaType.OPENDOCUMENT_TEXT);
		Collections.sort(mediaTypes, mediaTypeComparator);
		List<String> mimes = mediaTypes.stream().map(mt -> mt.toString()).collect(Collectors.toList());
		return mimes;
	}
	
	@Bean
	public List<String> avatarMimes() {
		List<MediaType> mediaTypes = Arrays.asList(MediaType.JPEG, MediaType.PNG, MediaType.TIFF);
		Collections.sort(mediaTypes, mediaTypeComparator);
		List<String> mimes = mediaTypes.stream().map(mt -> mt.toString()).collect(Collectors.toList());
		return mimes;
	}
	
	private Comparator<MediaType> mediaTypeComparator = new Comparator<MediaType>() {

		@Override
		public int compare(MediaType o1, MediaType o2) {
			// TODO Auto-generated method stub
			return o1.toString().compareTo(o2.toString());
		}
		
	};*/

}
