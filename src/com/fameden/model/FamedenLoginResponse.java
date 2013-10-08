package com.fameden.model;


public class FamedenLoginResponse extends GenericResponse {

	private String userName;

	private GenericResponse genericResponse;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public GenericResponse getGenericResponse() {
		return genericResponse;
	}

	public void setGenericResponse(GenericResponse genericResponse) {
		this.genericResponse = genericResponse;
	}

	
}
