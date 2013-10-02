package com.fameden.dao;

import java.sql.Date;
import java.util.Calendar;

import org.hibernate.Session;

import com.fameden.bean.FamedenUser;
import com.fameden.bean.FamedenUserIdsMap;
import com.fameden.bean.FamedenUserInfo;
import com.fameden.bean.FamedenUserKeys;
import com.fameden.bean.FamedenUserMappingCompositePK;
import com.fameden.dto.RegistrationDTO;
import com.fameden.util.DatabaseConfig;

public class UserRegistrationDAO implements ICommonDAO {

	public static void main(String[] args) {
		RegistrationDTO dto = new RegistrationDTO();
		dto.setCustomerIP("192.168.1.1");
		dto.setEmailAddress("ravjot28@gmail.com");
		dto.setFullName("Ravjot Singh");
		dto.setPassword("ravjot123");
		dto.setPrivateToken("ravjottoken1");
		dto.setPublicToken("ravjottoken2");
		dto.setRegistrationType("facebook");
		new UserRegistrationDAO().registerUser(dto);
	}

	public boolean registerUser(RegistrationDTO dto) {
		boolean result = false;
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		FamedenUserInfo famdenUserInfo = populateFamedenUserInfo(dto);
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

		return result;
	}

	@Override
	public Object populateDAOFromDTO(Object dto) {
		RegistrationDTO registrationDTO = (RegistrationDTO) dto;
		FamedenUserIdsMap famedenUserIdsMap = null;
		FamedenUserMappingCompositePK famedenUserMappingCompositePK = new FamedenUserMappingCompositePK();
		famedenUserIdsMap = new FamedenUserIdsMap();
		famedenUserMappingCompositePK.setFamedenUser(populateFamedenUserInfo(
				registrationDTO).getFamedenUser());
		famedenUserMappingCompositePK
				.setFamedenUserKeys(populateFamedenUserKeys(registrationDTO));

		famedenUserIdsMap
				.setFamedenUserMappingCompositePK(famedenUserMappingCompositePK);
		return famedenUserIdsMap;
	}

	public FamedenUser populateFamedenUser(RegistrationDTO dto) {
		FamedenUser famedenUser = null;
		famedenUser = new FamedenUser();
		famedenUser.setActive('Y');
		famedenUser.setIsVerified('N');
		famedenUser.setCreationDate(new Date(Calendar.getInstance()
				.getTimeInMillis()));
		famedenUser.setRegistrationMode(dto.getRegistrationType());
		famedenUser.setEmailAddress(dto.getEmailAddress());
		return famedenUser;
	}

	public FamedenUserKeys populateFamedenUserKeys(RegistrationDTO dto) {
		FamedenUserKeys famedenUserDetail = null;
		famedenUserDetail = new FamedenUserKeys();

		famedenUserDetail.setPassword(dto.getPassword());
		famedenUserDetail.setPrivateToken(dto.getPrivateToken());
		famedenUserDetail.setPubicToken(dto.getPublicToken());

		return famedenUserDetail;

	}

	public FamedenUserInfo populateFamedenUserInfo(RegistrationDTO dto) {
		FamedenUserInfo famedenUserInfo = null;
		famedenUserInfo = new FamedenUserInfo();
		famedenUserInfo.setFullName(dto.getFullName());
		famedenUserInfo.setFamedenUser(populateFamedenUser(dto));
		return famedenUserInfo;
	}

}
