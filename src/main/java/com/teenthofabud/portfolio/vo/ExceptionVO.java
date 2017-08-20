package com.teenthofabud.portfolio.vo;

public class ExceptionVO extends ErrorVO {
	
	private String cause;

	public ExceptionVO(String code, String cause, String message) {
		super(code, message);
		this.cause = cause;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

}
