package com.fameden.dto;

public class LoginDTO extends GenericDTO {

	private String userID ;
	
	private String password;
	
	private String LoginMode;

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
	

	
}
