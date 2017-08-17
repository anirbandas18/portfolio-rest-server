package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class ErrorVO {
	
	private Integer code;
	private String message;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorVO(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
}
