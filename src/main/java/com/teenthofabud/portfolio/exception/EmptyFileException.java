package com.teenthofabud.portfolio.exception;

public class EmptyFileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6473578091344606091L;

	public EmptyFileException() {
		super("Empty file contents");
	}

}
