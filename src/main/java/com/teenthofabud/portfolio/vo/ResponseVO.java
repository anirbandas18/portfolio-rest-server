package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "response")
@JsonInclude(value = Include.NON_EMPTY)
public class ResponseVO {
	
	public ResponseVO(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	private String status;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseVO(String message) {
		super();
		this.message = message;
	}

	public ResponseVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
