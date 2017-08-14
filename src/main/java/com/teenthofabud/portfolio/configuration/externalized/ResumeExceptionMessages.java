package com.teenthofabud.portfolio.configuration.externalized;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resume")
public class ResumeExceptionMessages {
	
	private String resumeExceptionTemplate;
	private String resumeContentAbsent;
	public String getResumeExceptionTemplate() {
		return resumeExceptionTemplate;
	}
	public void setResumeExceptionTemplate(String resumeExceptionTemplate) {
		this.resumeExceptionTemplate = resumeExceptionTemplate;
	}
	public String getResumeContentAbsent() {
		return resumeContentAbsent;
	}
	public void setResumeContentAbsent(String resumeContentAbsent) {
		this.resumeContentAbsent = resumeContentAbsent;
	}

}
