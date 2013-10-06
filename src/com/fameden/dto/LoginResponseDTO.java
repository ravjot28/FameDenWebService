package com.fameden.dto;

public class LoginResponseDTO extends GenericResponseDTO {

	private String userName;

	private GenericResponseDTO genericResponseDTO;

	public String getUserName() {
		return userName;
	}

	public GenericResponseDTO getGenericResponseDTO() {
		return genericResponseDTO;
	}

	public void setGenericResponseDTO(GenericResponseDTO genericResponseDTO) {
		this.genericResponseDTO = genericResponseDTO;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
