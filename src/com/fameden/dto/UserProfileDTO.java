package com.fameden.dto;

public class UserProfileDTO extends GenericResponseDTO {

	private String userName;

	private GenericResponseDTO genericResponseDTO;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
