package com.fameden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.GlobalConstants;
import com.fameden.constants.LoginConstants;
import com.fameden.dao.CommonRequestOperationDAO;
import com.fameden.dao.LoginDAO;
import com.fameden.dto.LoginDTO;
import com.fameden.dto.LoginRequestDTO;
import com.fameden.dto.LoginResponseDTO;
import com.fameden.exception.LoginValidationException;
import com.fameden.model.FamedenLoginResponse;
import com.fameden.util.CommonValidations;

public class LoginService implements ICommonService{

	Logger logger = LoggerFactory.getLogger(LoginService.class);


	@SuppressWarnings("null")
	@Override
	public Object processRequest(Object obj) {

		LoginDTO dto = (LoginDTO) obj;
		boolean validation = false;
		String errorMessage = null;
		int requestId = -1;
		
		
		try {
			validation = validate(dto);
		} catch (LoginValidationException e) {
			logger.error(e.getMessage(), e);
			errorMessage = e.getMessage();
		} 

		if (!validation && errorMessage != null) {
			dto.setStatus(GlobalConstants.FAILURE);
			dto.setMessage(errorMessage);

		} else {
			
			FamedenRequestDetail famedenRequestDetail = (FamedenRequestDetail) populateRequest(dto);
			
			try {
				requestId = insertRequest(famedenRequestDetail);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (requestId !=-1) {
				
				LoginDAO loginDAO = new LoginDAO();
				
				try {
					dto = (LoginDTO)loginDAO.authenticateAndFetchUserProfile(dto);
				} catch (Exception e) {
					dto.setStatus(GlobalConstants.FAILURE);
					dto.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
				}
				
				/*if (dto != null) {
					dto.setStatus(GlobalConstants.SUCCESS);
				}else {
					dto.setStatus(GlobalConstants.FAILURE);
					dto.setMessage(errorMessage);
				}*/
	
			}
			
		}
		
		CommonRequestOperationDAO codao = new CommonRequestOperationDAO();
		try {
			if (dto.getStatus().equals(GlobalConstants.SUCCESS)) {
				codao.updateRequestStatus(requestId, GlobalConstants.SUCCESS);
			} else if (requestId != -1) {
				codao.updateRequestStatus(requestId, GlobalConstants.FAILURE);
			}
		} catch (Exception e) {
			dto.setStatus(GlobalConstants.FAILURE);
			dto.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
		}
		
		FamedenLoginResponse response = (FamedenLoginResponse) populateResponse(dto);
		

		return response;

	}


	@Override
	public boolean validate(Object obj) throws LoginValidationException {
		
		LoginDTO dto = (LoginDTO) obj;
		boolean result = false;

		if (!CommonValidations.isStringEmpty(dto.getEmailAddress())) {
			if (!CommonValidations.isStringEmpty(dto.getPassword())) {
				if (!CommonValidations.isStringEmpty(dto.getLoginMode())) {
					result = true;
				} else {
					throw new LoginValidationException(
							LoginConstants.invalidLoginModeMessage);
				}
			} else {
				throw new LoginValidationException(
						LoginConstants.invalidPasswordMessage);
			}

		} else {
			throw new LoginValidationException(
					LoginConstants.invalidUserIDMessage);

		}
		/*
		 * SaltTextEncryption ste = new SaltTextEncryption();
		 * 
		 * String hash = ste.createHash("ravjot28");
		 * 
		 * System.out.println(hash);
		 * 
		 * System.out.println(ste.validatePassword("ravjot2.8", hash));
		 */

		return result;
		
	}

	@Override
	public Object populateResponse(Object obj) {
		
		FamedenLoginResponse response = null ;
		LoginDTO dto = (LoginDTO) obj;
		
		if (dto != null) {
			response = new FamedenLoginResponse();
			response.setStatus(dto.getStatus());
			response.setTransactionId(dto.getTransactionId());
			response.setErrorMessage(dto.getMessage());
			
			response.setUserName(dto.getEmailAddress());
		}
		
		return response;
	}

	@Override
	public int insertRequest(FamedenRequestDetail famedenRequestDetail)
			throws Exception {
		int requestId = -1;
		CommonRequestOperationDAO commonRequestOperationDAO = new CommonRequestOperationDAO();
		try {
			requestId = commonRequestOperationDAO
					.insertRequest(famedenRequestDetail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return requestId;
	}

	@Override
	public Object populateRequest(Object obj) {
		FamedenRequestDetail famedenRequestDetail = null;
		FamedenRequest famedenRequest = null;
		LoginDTO dto = (LoginDTO) obj;
		if (dto != null) {
			famedenRequest = new FamedenRequest();
			famedenRequest.setCustomerIP(dto.getCustomerIP());
			famedenRequest.setRequestStatus(GlobalConstants.IN_PROCESS);
			famedenRequest.setRequestType(GlobalConstants.Login_REQUEST);
			famedenRequest.setRequestUser(dto.getEmailAddress());
			famedenRequest.setFamedenUserId(0);
			famedenRequestDetail = new FamedenRequestDetail();
			famedenRequestDetail.setFamedenRequest(famedenRequest);
		}
		return famedenRequestDetail;
		
	}
	
}
