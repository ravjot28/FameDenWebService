package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.hibernate.Session;

import com.fameden.bean.FamedenUser;
import com.fameden.bean.FamedenUserIdsMap;
import com.fameden.bean.FamedenUserInfo;
import com.fameden.bean.FamedenUserKeys;
import com.fameden.bean.FamedenUserMappingCompositePK;
import com.fameden.dto.LoginDTO;
import com.fameden.dto.UserProfileDTO;
import com.fameden.util.DatabaseConfig;
import com.fameden.util.SaltTextEncryption;

public class LoginDAO {

	public static void main(String[] args) {
		LoginDTO login = new LoginDTO();
		
		login.setEmailAddress("ravjot28@gmail.com");
		
		try {
			authenticateAndFetchUserProfile(login);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object authenticateAndFetchUserProfile(
			LoginDTO loginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {

		boolean isAuthenticationSuccessful = false;
		//FamedenUserInfo userProfileBean = new FamedenUserInfo();
		
		FamedenUser user = new FamedenUser();
		FamedenUserIdsMap userIdMap= new FamedenUserIdsMap();
		FamedenUserKeys userCredentials = new FamedenUserKeys();
		
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();
		
		session.beginTransaction();
		
		//user = (FamedenUser)session.get(FamedenUser.class, loginDTO.getEmailAddress());
		
		if (1 == 2)  {
			
			
			
		}else {
			
			userIdMap = (FamedenUserIdsMap) session.get(FamedenUserIdsMap.class, 10);
			FamedenUserMappingCompositePK compositePK = userIdMap.getFamedenUserMappingCompositePK();
			userCredentials = (FamedenUserKeys)session.get(FamedenUserKeys.class, compositePK.getFamedenUserKeys().getFamdenInternalUserId());
			
			System.out.println(userCredentials.getPassword());
			
		}
		
		
		//isAuthenticationSuccessful = this.authenticateUser(loginDTO,Session session);

		if (isAuthenticationSuccessful) {
			
			//this.populateUserProfileDTO();

			//userProfileBean = this.fetchUserProfile(loginBean.getUserID());

		} else {

		}
		
		session.getTransaction().commit();

		return null;

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
