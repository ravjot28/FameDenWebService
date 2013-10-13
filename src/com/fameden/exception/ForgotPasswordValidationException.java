package com.fameden.exception;

public class ForgotPasswordValidationException extends Exception {

private static final long serialVersionUID = 2L;
	
	public ForgotPasswordValidationException(String message){
		super(message);
	}
	
}
