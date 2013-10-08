package com.fameden.dao;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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
import com.fameden.dto.LoginRequestDTO;
import com.fameden.dto.LoginResponseDTO;
import com.fameden.util.DatabaseConfig;
import com.fameden.util.RSAEncryptionKeyGeneration;
import com.fameden.util.SaltTextEncryption;

public class LoginDAO {

	Logger logger = LoggerFactory.getLogger(UserRegistrationDAO.class);

	public LoginResponseDTO authenticateAndFetchUserProfile(
			LoginRequestDTO loginDTO) throws Exception {

		LoginResponseDTO loginResponse = null;
		// FamedenRequestDetail requestDetail = null;
		FamedenUser user = null;
		// = new FamedenUserMappingCompositePK();
		CommonUserOperation emailPresenceCheck = null;// new
														// CommonUserOperation();
		Session session = null;

		try {
			emailPresenceCheck = new CommonUserOperation();
			user = emailPresenceCheck.searchByEmailId(loginDTO
					.getEmailAddress());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		loginResponse = new LoginResponseDTO();
		if (user == null) {
			loginResponse.setStatus(GlobalConstants.FAILURE);
			loginResponse.setMessage(LoginConstants.loginFailed);

		} else {

			try {

				session = DatabaseConfig.getSessionFactory().openSession();

				session.beginTransaction();

				String encryptedPasswordFromDB = null;
				String encryptedPasswordFromUI = null;

				encryptedPasswordFromDB = this.getPasswordFromDB(session, user);

				if (encryptedPasswordFromDB != null) {
					boolean isAuthenticatedUser = this.authenticateUser(loginDTO) ;
						
					if (isAuthenticatedUser) {

						loginResponse = this.populateResponseDTO(session, user);
						// TODO Fetch User Profile
					} else {
						loginResponse.setStatus(GlobalConstants.FAILURE);
						loginResponse.setMessage(LoginConstants.loginFailed);
					}

				} else {
					try {
						loginResponse.setStatus(GlobalConstants.FAILURE);
						loginResponse.setMessage(LoginConstants.loginFailed);

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw e;
					}
					loginResponse.setStatus(GlobalConstants.FAILURE);
					loginResponse.setMessage(LoginConstants.loginFailed);

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
		return loginResponse;

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

	private LoginResponseDTO populateResponseDTO(Session session,
			FamedenUser user) {
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		FamedenUserInfo userInfo = new FamedenUserInfo();
		userInfo = (FamedenUserInfo) session.get(FamedenUserInfo.class,
				user.getFamdenExternalUserId());

		responseDTO.setUserName(userInfo.getFullName());
		responseDTO.setStatus(GlobalConstants.SUCCESS);

		return responseDTO;
	}

	private boolean authenticateUser(LoginRequestDTO loginDTO)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		boolean isLoginSuccessful = false;

		try {
			if (!RSAEncryptionKeyGeneration.areKeysPresent()) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isLoginSuccessful;
	}

}
