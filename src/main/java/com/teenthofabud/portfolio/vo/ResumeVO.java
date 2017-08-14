package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "resume")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResumeVO {

	private Long size;
	private String checkSum;
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	
}