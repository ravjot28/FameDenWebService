package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenUser;
import com.fameden.bean.FamedenUserIdsMap;
import com.fameden.bean.FamedenUserInfo;
import com.fameden.bean.FamedenUserKeys;
import com.fameden.bean.FamedenUserMappingCompositePK;
import com.fameden.constants.GlobalConstants;
import com.fameden.constants.LoginConstants;
import com.fameden.dto.LoginDTO;
import com.fameden.util.DatabaseConfig;

public class LoginDAO {

	Logger logger = LoggerFactory.getLogger(UserRegistrationDAO.class);

	public LoginDTO authenticateAndFetchUserProfile(
			LoginDTO loginDTO) throws Exception {

		Session session = null;
		FamedenUser user = null;
	
		CommonUserOperation emailPresenceCheck = null;

		try {
			emailPresenceCheck = new CommonUserOperation();
			user = emailPresenceCheck.searchByEmailId(loginDTO
					.getEmailAddress());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (user == null) {
			loginDTO.setStatus(GlobalConstants.FAILURE);
			loginDTO.setMessage(LoginConstants.loginFailed);

		} else {

			try {

				session = DatabaseConfig.getSessionFactory().openSession();

				session.beginTransaction();

				String encryptedPasswordFromDB = null;
				//String encryptedPasswordFromUI = null;
				
				
				
				encryptedPasswordFromDB = this.getPasswordFromDB(session, user);

				if (encryptedPasswordFromDB != null) {
					
					boolean isAuthenticatedUser = this.authenticateUser(session,user, loginDTO) && encryptedPasswordFromDB.equals(loginDTO.getPassword());
						
					if (isAuthenticatedUser) {

						loginDTO = this.populateResponseDTO(session, user);
						// TODO Fetch User Profile
					} else {
						loginDTO.setStatus(GlobalConstants.FAILURE);
						loginDTO.setMessage(LoginConstants.loginFailed);
					}

				} else {
					try {
						loginDTO.setStatus(GlobalConstants.FAILURE);
						loginDTO.setMessage(LoginConstants.loginFailed);

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw e;
					}
					loginDTO.setStatus(GlobalConstants.FAILURE);
					loginDTO.setMessage(LoginConstants.loginFailed);

				}
				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return loginDTO;

	}

	private String getPasswordFromDB(Session session, FamedenUser user) {

		String encryptedPassword = "";
		FamedenUserKeys userCredentials = new FamedenUserKeys();
		FamedenUserMappingCompositePK userIdCompositePK = new FamedenUserMappingCompositePK();

		Criteria crit = session.createCriteria(FamedenUserIdsMap.class);
		Criterion externalIdRestriction = Restrictions
				.eq("famedenUserMappingCompositePK.famedenUser.famdenExternalUserId",
						user.getFamdenExternalUserId());
		crit.add(externalIdRestriction);

		List famedenUserIdMappingList = crit.list();

		if (famedenUserIdMappingList != null
				&& famedenUserIdMappingList.size() > 0) {
			FamedenUserIdsMap idMap = (FamedenUserIdsMap) famedenUserIdMappingList
					.get(0);
			userIdCompositePK = idMap.getFamedenUserMappingCompositePK();

			userCredentials = (FamedenUserKeys) session.get(
					FamedenUserKeys.class, userIdCompositePK
							.getFamedenUserKeys().getFamdenInternalUserId());
		}
		encryptedPassword = userCredentials.getPassword();
		return encryptedPassword;
	}

	private LoginDTO populateResponseDTO(Session session,
			FamedenUser user) {
		LoginDTO loginDTO = new LoginDTO();
		FamedenUserInfo userInfo = new FamedenUserInfo();
		userInfo = (FamedenUserInfo) session.get(FamedenUserInfo.class,
				user.getFamdenExternalUserId());

		//loginDTO.setUserID(userInfo.get);(userInfo.getFullName());
		loginDTO.setStatus(GlobalConstants.SUCCESS);

		return loginDTO;
	}
	

	private boolean authenticateUser(Session session, FamedenUser user, LoginDTO loginDTO)
			//throws NoSuchAlgorithmException, InvalidKeySpecException
			{
		boolean isUserActive =false;
		boolean isLoginModeCorrect = false;
		boolean isPasswordCorrect = true;
		boolean isLoginSuccessful = false;

		try {
			
			if (user.getRegistrationMode().equals(loginDTO.getLoginMode())) {
				isLoginModeCorrect = true;
			}
			if (user.getActive()=='Y') {
				isUserActive = true;
			}
			
			
/*			if (!RSAEncryptionKeyGeneration.areKeysPresent()) {
				RSAEncryptionKeyGeneration.generateKey();
			}

			final String rsaEncryptedPassword = loginDTO.getPassword();
			ObjectInputStream inputStream = null;
			inputStream = new ObjectInputStream(new FileInputStream(
					RSAEncryptionKeyGeneration.PUBLIC_KEY_FILE));
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			final byte[] cipherText = RSAEncryptionKeyGeneration.encrypt(
					rsaEncryptedPassword, publicKey);
			inputStream = new ObjectInputStream(new FileInputStream(
					RSAEncryptionKeyGeneration.PRIVATE_KEY_FILE));
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final String userPassword = RSAEncryptionKeyGeneration.decrypt(
					cipherText, privateKey);

			SaltTextEncryption ste = new SaltTextEncryption();
			String saltEncryptedPassword = ste.createHash(userPassword);
			isLoginSuccessful = ste.validatePassword("ravjot2.8",
					saltEncryptedPassword);
*/
			if (isUserActive&& isLoginModeCorrect && isPasswordCorrect) {
				isLoginSuccessful = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isLoginSuccessful;
	}

}
