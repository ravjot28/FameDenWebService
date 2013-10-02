package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fameden.bean.FamedenUserInfo;
import com.fameden.dto.LoginDTO;
import com.fameden.dto.UserProfileDTO;
import com.fameden.util.SaltTextEncryption;

public class LoginDAO {

	public Object authenticateAndFetchUserProfile(
			LoginDTO loginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {

		boolean isAuthenticationSuccessful = false;
		FamedenUserInfo userProfileBean = new FamedenUserInfo();

		isAuthenticationSuccessful = this.authenticateUser(loginDTO);

		if (isAuthenticationSuccessful) {
			
			//this.populateUserProfileDTO();

			//userProfileBean = this.fetchUserProfile(loginBean.getUserID());

		} else {

		}

		return userProfileBean;

	}



	private UserProfileDTO populateUserProfileDTO(FamedenUserInfo userProfileBean) {

		UserProfileDTO userProfileDTO = new UserProfileDTO();
		//userProfileDTO.setUserName(userProfileBean.getUserName());

		return null;
	}
	
	private boolean authenticateUser(LoginDTO loginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		boolean isLoginSuccessful = false ;
		String actualPassword ="ravjot28";
		
		//TODO: Get Actual Password from db
		
		SaltTextEncryption ste = new SaltTextEncryption();
		
		String hash = ste.createHash(loginDTO.getPassword());
		
		isLoginSuccessful = ste.validatePassword(actualPassword, hash);

		return isLoginSuccessful;
	}

	

}
