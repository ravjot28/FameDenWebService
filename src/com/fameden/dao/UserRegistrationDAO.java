package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.Calendar;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenUser;
import com.fameden.bean.FamedenUserIdsMap;
import com.fameden.bean.FamedenUserInfo;
import com.fameden.bean.FamedenUserKeys;
import com.fameden.bean.FamedenUserMappingCompositePK;
import com.fameden.dto.RegistrationDTO;
import com.fameden.util.DatabaseConfig;
import com.fameden.util.SaltTextEncryption;

public class UserRegistrationDAO {

	Logger logger = LoggerFactory.getLogger(UserRegistrationDAO.class);


	public String registerUser(RegistrationDTO dto) throws Exception {
		String verificationCode = null;
		Session session = null;
		FamedenUserInfo famdenUserInfo = null;
		try {
			famdenUserInfo = populateFamedenUserInfo(dto);
		} catch (Exception e) {
			throw e;
		}
		try {
			session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();
			FamedenUser famdenUser = famdenUserInfo.getFamedenUser();
			FamedenUserKeys famedenUserKeys = populateFamedenUserKeys(dto);

			FamedenUserIdsMap famedenUserIdsMap = null;
			FamedenUserMappingCompositePK famedenUserMappingCompositePK = new FamedenUserMappingCompositePK();
			famedenUserIdsMap = new FamedenUserIdsMap();

			famedenUserMappingCompositePK.setFamedenUser(famdenUser);
			famedenUserMappingCompositePK.setFamedenUserKeys(famedenUserKeys);

			famedenUserIdsMap
					.setFamedenUserMappingCompositePK(famedenUserMappingCompositePK);

			session.save(famdenUser);
			session.save(famdenUserInfo);
			session.save(famedenUserKeys);
			session.save(famedenUserIdsMap);

			session.getTransaction().commit();

			verificationCode = famdenUser.getVerificationCode();
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			logger.error(e.getMessage(), e);
			throw e;
		}

		return verificationCode;
	}

	public FamedenUser populateFamedenUser(RegistrationDTO dto)
			throws Exception {
		FamedenUser famedenUser = null;
		if (dto != null) {
			famedenUser = new FamedenUser();
			famedenUser.setActive('N');
			famedenUser.setIsVerified('N');
			famedenUser.setCreationDate(new Date(Calendar.getInstance()
					.getTimeInMillis()));
			famedenUser.setRegistrationMode(dto.getRegistrationType());
			famedenUser.setEmailAddress(dto.getEmailAddress());

			SaltTextEncryption encrypt = new SaltTextEncryption();
			String verificationCode = null;
			try {
				verificationCode = encrypt.createHash(dto.getEmailAddress());
				famedenUser.setVerificationCode(verificationCode);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return famedenUser;
	}

	public FamedenUserKeys populateFamedenUserKeys(RegistrationDTO dto) {
		FamedenUserKeys famedenUserDetail = null;
		if (dto != null) {
			famedenUserDetail = new FamedenUserKeys();

			famedenUserDetail.setPassword(dto.getPassword());
			famedenUserDetail.setPrivateToken(dto.getPrivateToken());
			famedenUserDetail.setPubicToken(dto.getPublicToken());
		}
		return famedenUserDetail;

	}

	public FamedenUserInfo populateFamedenUserInfo(RegistrationDTO dto)
			throws Exception {
		FamedenUserInfo famedenUserInfo = null;
		if (dto != null) {
			famedenUserInfo = new FamedenUserInfo();
			famedenUserInfo.setFullName(dto.getFullName());
			famedenUserInfo.setAlternateEmailAddress(dto
					.getAlternateEmailAddress());
			try {
				famedenUserInfo.setFamedenUser(populateFamedenUser(dto));
			} catch (Exception e) {
				throw e;
			}
		}
		return famedenUserInfo;
	}

}
