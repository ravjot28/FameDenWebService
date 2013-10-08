package com.fameden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.GlobalConstants;
import com.fameden.constants.LoginConstants;
import com.fameden.dao.CommonRequestOperationDAO;
import com.fameden.dao.LoginDAO;
import com.fameden.dto.LoginRequestDTO;
import com.fameden.dto.LoginResponseDTO;
import com.fameden.exception.LoginValidationException;
import com.fameden.model.FamedenLoginRequest;
import com.fameden.model.FamedenLoginResponse;
import com.fameden.util.CommonValidations;

public class LoginService implements ICommonService{

	Logger logger = LoggerFactory.getLogger(LoginService.class);


	@SuppressWarnings("null")
	@Override
	public Object processRequest(Object obj) {

		LoginRequestDTO loginDTO = (LoginRequestDTO) obj;
		boolean validation = false;
		String errorMessage = null;
		int requestId = -1;
		LoginResponseDTO loginResponseDTO = null;
		
		try {
			validation = validate(loginDTO);
		} catch (LoginValidationException e) {
			logger.error(e.getMessage(), e);
			errorMessage = e.getMessage();
		} 

		if (!validation && errorMessage != null) {
			loginDTO.setStatus(GlobalConstants.FAILURE);
			loginDTO.setMessage(errorMessage);

		} else {
			
			FamedenRequestDetail famedenRequestDetail = (FamedenRequestDetail) populateRequest(loginDTO);
			
			try {
				requestId = insertRequest(famedenRequestDetail);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (requestId !=-1) {
				
				LoginDAO loginDAO = new LoginDAO();
				
				try {
					loginResponseDTO = (LoginResponseDTO)loginDAO.authenticateAndFetchUserProfile(loginDTO);
				} catch (Exception e) {
					loginResponseDTO.setStatus(GlobalConstants.FAILURE);
					loginResponseDTO.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
				}
				
				if (loginResponseDTO != null) {
					loginResponseDTO.setStatus(GlobalConstants.SUCCESS);
				}else {
					loginResponseDTO.setStatus(GlobalConstants.FAILURE);
					loginResponseDTO.setMessage(errorMessage);
				}
	
			}
			
		}
		
		CommonRequestOperationDAO codao = new CommonRequestOperationDAO();
		try {
			if (loginResponseDTO.getStatus().equals(GlobalConstants.SUCCESS)) {
				codao.updateRequestStatus(requestId, GlobalConstants.SUCCESS);
			} else if (requestId != -1) {
				codao.updateRequestStatus(requestId, GlobalConstants.FAILURE);
			}
		} catch (Exception e) {
			loginResponseDTO.setStatus(GlobalConstants.FAILURE);
			loginResponseDTO.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
		}
		
		FamedenLoginResponse response = (FamedenLoginResponse) populateResponse(loginResponseDTO);
		

		return response;

	}


	@Override
	public boolean validate(Object obj) throws LoginValidationException {
		
		LoginRequestDTO loginDTO = (LoginRequestDTO) obj;
		boolean result = false;

		if (!CommonValidations.isStringEmpty(loginDTO.getEmailAddress())) {
			if (!CommonValidations.isStringEmpty(loginDTO.getPassword())) {
				if (!CommonValidations.isStringEmpty(loginDTO.getLoginMode())) {
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
		LoginResponseDTO dto = (LoginResponseDTO) obj;
		
		if (dto != null) {
			response = new FamedenLoginResponse();
			response.setStatus(dto.getStatus());
			response.setTransactionId(dto.getTransactionId());
			response.setErrorMessage(dto.getMessage());
			
			response.setUserName(dto.getUserName());
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
		FamedenLoginRequest dto = (FamedenLoginRequest) obj;
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
