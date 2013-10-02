package com.fameden.bean;

public class FamedenLogin {

	private String userID ;
	
	private String password;
	
	private String loginMode;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}  
	
	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	
	
}
