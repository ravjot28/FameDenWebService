package com.fameden.exception;

public class LoginValidationException extends Exception {

	private static final long serialVersionUID = 2L;
	
	public LoginValidationException(String message){
		super(message);
	}
	
}
