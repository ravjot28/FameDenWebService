package com.fameden.model;


public class FamedenLoginModel extends GenericRequest{

	
	
	
	private String password;
	private String loginMode;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}
}
