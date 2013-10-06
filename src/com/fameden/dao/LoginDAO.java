package com.fameden.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
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
import com.fameden.util.SaltTextEncryption;

public class LoginDAO {

	public static void main(String[] args) {
		LoginRequestDTO login = new LoginRequestDTO();

		login.setEmailAddress("arora.puneet777@gmail.com");
		login.setPassword("1234");

		try {
			LoginDAO dao = new LoginDAO();
			dao.authenticateAndFetchUserProfile(login);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object authenticateAndFetchUserProfile(LoginRequestDTO loginDTO)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		LoginResponseDTO loginResponse = new LoginResponseDTO();
		// boolean isAuthenticationSuccessful = false;
		int requestID = -1;
		FamedenRequestDetail requestDetail = null;
		FamedenUser user = new FamedenUser();
		// FamedenUserIdsMap userIdMap = new FamedenUserIdsMap();
		FamedenUserKeys userCredentials = new FamedenUserKeys();
		FamedenUserMappingCompositePK userIdCompositePK = new FamedenUserMappingCompositePK();
		CommonUserOperation emailPresenceCheck = new CommonUserOperation();

		requestDetail = this.populateRequestBeanFromDTO(loginDTO);
		CommonRequestOperationDAO loginRequest = new CommonRequestOperationDAO();

		try {
			requestID = loginRequest.insertRequest(requestDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = emailPresenceCheck.searchByEmailId(loginDTO.getEmailAddress());
		
		if (user == null) {
			try {
				
				loginRequest.updateRequestStatus(requestID,
						GlobalConstants.FAILURE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loginResponse.setStatus(GlobalConstants.FAILURE);
			loginResponse.setMessage(LoginConstants.userNameNotFound
					+ loginDTO.getEmailAddress());

		} else {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(FamedenUserIdsMap.class);
			Criterion externalIdRestriction = Restrictions
					.eq("famedenUserMappingCompositePK.famedenUser.famdenExternalUserId",
							user.getFamdenExternalUserId());
			crit.add(externalIdRestriction);
			List<FamedenUserIdsMap> famedenUserIdMappingList = ((List<FamedenUserIdsMap>) crit
					.list());

			if (famedenUserIdMappingList != null
					&& famedenUserIdMappingList.size() > 0) {
				userIdCompositePK = famedenUserIdMappingList.get(0)
						.getFamedenUserMappingCompositePK();

				System.out.println(userIdCompositePK.getFamedenUserKeys()
						.getFamdenInternalUserId());
				userCredentials = (FamedenUserKeys) session
						.get(FamedenUserKeys.class, userIdCompositePK
								.getFamedenUserKeys().getFamdenInternalUserId());

				if (loginDTO.getPassword().compareTo(
						userCredentials.getPassword()) == 0) {

					FamedenUserInfo userInfo = new FamedenUserInfo();
					userInfo = (FamedenUserInfo) session.get(
							FamedenUserInfo.class,
							user.getFamdenExternalUserId());

					try {
						loginRequest.updateRequestStatus(requestID,
								GlobalConstants.SUCCESS);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					try {
						loginRequest.updateRequestStatus(requestID,
								GlobalConstants.FAILURE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					loginResponse.setStatus(GlobalConstants.FAILURE);
					loginResponse.setMessage(LoginConstants.passwordIncorrect
							+ loginDTO.getEmailAddress());
				}

			}
			session.getTransaction().commit();
			session.close();
		}

		return null;

	}

	private FamedenRequestDetail populateRequestBeanFromDTO(Object obj) {
		LoginRequestDTO loginDTO = (LoginRequestDTO) obj;
		FamedenRequestDetail requestDetail = new FamedenRequestDetail();
		FamedenRequest request = new FamedenRequest();

		request.setCustomerIP(loginDTO.getCustomerIP());
		request.setRequestDate(new Date(Calendar.getInstance()
				.getTimeInMillis()));
		request.setFamedenUserId(0);
		request.setRequestStatus(GlobalConstants.IN_PROCESS);
		request.setRequestType(GlobalConstants.Login_REQUEST);
		request.setRequestUpdateDate(new Date(Calendar.getInstance()
				.getTimeInMillis()));
		request.setRequestUser(loginDTO.getEmailAddress());

		requestDetail.setFamedenRequest(request);

		return requestDetail;
	}

	private boolean authenticateUser(LoginRequestDTO loginDTO)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		boolean isLoginSuccessful = false;
		String actualPassword = "ravjot28";

		// TODO: Get Actual Password from db

		SaltTextEncryption ste = new SaltTextEncryption();

		String hash = ste.createHash(loginDTO.getPassword());

		isLoginSuccessful = ste.validatePassword(actualPassword, hash);

		return isLoginSuccessful;
	}

}
