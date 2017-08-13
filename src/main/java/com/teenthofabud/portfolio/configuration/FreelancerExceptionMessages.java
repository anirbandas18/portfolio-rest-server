package com.teenthofabud.portfolio.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("freelancer")
public class FreelancerExceptionMessages {
	
	private String detailsInvalid;
	private String detailsEmpty;
	private String exceptionTemplate;
	public String getDetailsInvalid() {
		return detailsInvalid;
	}
	public void setDetailsInvalid(String detailsInvalid) {
		this.detailsInvalid = detailsInvalid;
	}
	public String getDetailsEmpty() {
		return detailsEmpty;
	}
	public void setDetailsEmpty(String detailsEmpty) {
		this.detailsEmpty = detailsEmpty;
	}
	public String getExceptionTemplate() {
		return exceptionTemplate;
	}
	public void setExceptionTemplate(String exceptionTemplate) {
		this.exceptionTemplate = exceptionTemplate;
	}
	
	

}
