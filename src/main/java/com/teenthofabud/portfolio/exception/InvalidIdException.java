package com.teenthofabud.portfolio.exception;

public class InvalidIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3000268495662720999L;

	public InvalidIdException(String id) {
		super(String.format("Non-existent id %s", id));
		// TODO Auto-generated constructor stub
	}


}
