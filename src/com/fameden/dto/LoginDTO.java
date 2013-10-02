package com.fameden.dto;

public class LoginDTO {
	
	private String userID ;
	
	private String password;
	
	private String LoginMode;
	
	private String ExceptionMessage;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMode() {
		return LoginMode;
	}

	public void setLoginMode(String loginMode) {
		LoginMode = loginMode;
	}

	public String getExceptionMessage() {
		return ExceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		ExceptionMessage = exceptionMessage;
	}


}
