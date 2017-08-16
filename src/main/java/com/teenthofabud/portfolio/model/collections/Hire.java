package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Hire {
	
	@Id
	@JsonIgnore
	private String id;
	private Double consultationRate;
	private Double developmentRate;
	private String paymentURL;
	private List<String> services;
	private List<String> style;
	public Double getConsultationRate() {
		return consultationRate;
	}
	public void setConsultationRate(Double consultationRate) {
		this.consultationRate = consultationRate;
	}
	public Double getDevelopmentRate() {
		return developmentRate;
	}
	public void setDevelopmentRate(Double developmentRate) {
		this.developmentRate = developmentRate;
	}
	public String getPaymentURL() {
		return paymentURL;
	}
	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
	}
	public List<String> getServices() {
		return services;
	}
	public void setServices(List<String> services) {
		this.services = services;
	}
	public List<String> getStyle() {
		return style;
	}
	public void setStyle(List<String> style) {
		this.style = style;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
