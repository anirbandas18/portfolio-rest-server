package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "response")
@JsonInclude(value = Include.NON_EMPTY)
public class ResponseVO {
	
	public ResponseVO(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private String code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResponseVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
