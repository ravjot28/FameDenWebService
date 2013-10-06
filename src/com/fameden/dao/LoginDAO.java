package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
		
		CommonUserOperation emailPresenceCheck = new  CommonUserOperation();
		
		FamedenUser user = new FamedenUser();
		FamedenUserIdsMap userIdMap= new FamedenUserIdsMap();
		FamedenUserKeys userCredentials = new FamedenUserKeys();
		FamedenUserMappingCompositePK userIdCompositePK = new FamedenUserMappingCompositePK();
		
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();
		
		session.beginTransaction();
		
		user = emailPresenceCheck.searchByEmailId(loginDTO.getEmailAddress());
		//System.out.println(user.getRegistrationMode());
		if (user==null)  {
			
			
			
		}else {
			Criteria crit = session.createCriteria(FamedenUserIdsMap.class);
			//crit.createAlias("famedenUser", "user");
			//System.out.println(user.getFamdenExternalUserId());
			Criterion externalIdRestriction = Restrictions
					.eq("famedenUserMappingCompositePK.famedenUser.famdenExternalUserId", user.getFamdenExternalUserId());
			crit.add(externalIdRestriction);
			List<FamedenUserIdsMap> famedenUserIdMappingList = ((List<FamedenUserIdsMap>) crit
					.list());

			if (famedenUserIdMappingList != null
					&& famedenUserIdMappingList.size() > 0) {
				userIdCompositePK = famedenUserIdMappingList.get(0).getFamedenUserMappingCompositePK();
				
				System.out.println(userIdCompositePK.getFamedenUserKeys().getFamdenInternalUserId());
				userCredentials = (FamedenUserKeys)session.get(FamedenUserKeys.class, userIdCompositePK.getFamedenUserKeys().getFamdenInternalUserId());
				System.out.println(userCredentials.getPassword());
			}else{
				System.out.println("empty list");
			}
			
			


			/*userIdMap = (FamedenUserIdsMap) session.get(FamedenUserIdsMap.class, 10);
			FamedenUserMappingCompositePK compositePK = userIdMap.getFamedenUserMappingCompositePK();
			userCredentials = (FamedenUserKeys)session.get(FamedenUserKeys.class, compositePK.getFamedenUserKeys().getFamdenInternalUserId());
			*/
			//System.out.println(userCredentials.getPassword());
			
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
