package com.fameden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.ForgotPasswordConstants;
import com.fameden.constants.GlobalConstants;
import com.fameden.dto.ForgotPasswordDTO;
import com.fameden.dto.RegistrationDTO;
import com.fameden.exception.ForgotPasswordValidationException;
import com.fameden.exception.RegistrationValidationException;
import com.fameden.model.FamedenForgotPasswordResponse;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.util.CommonValidations;

public class ForgotPasswordService implements ICommonService {

	Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
	
	@Override
	public boolean validate(Object obj) throws ForgotPasswordValidationException {
		ForgotPasswordDTO dto = (ForgotPasswordDTO) obj;
		boolean result = false;
		if (!CommonValidations.isStringEmpty(dto.getEmailAddress())
				&& CommonValidations.isValidEmailAddress(dto.getEmailAddress())){
			result = true;
			
		}else{
			throw new ForgotPasswordValidationException(ForgotPasswordConstants.invalidUserIDMessage);
		}
		return result;
	}

	@Override
	public Object processRequest(Object obj) {
		
		ForgotPasswordDTO dto = (ForgotPasswordDTO) obj;
		boolean validation = false;
		int requestId = -1;
		String errorMessage = null;
		try {
			validation = validate(dto);
		} catch (ForgotPasswordValidationException e) {
			logger.error(e.getMessage(), e);
			errorMessage = e.getMessage();
		} 
		if (!validation && errorMessage !=null) {
			dto.setStatus(GlobalConstants.FAILURE);
			dto.setMessage(errorMessage);
		}else {
			FamedenRequestDetail famedenRequestDetail = (FamedenRequestDetail) populateRequest(dto);
			
			try {
				requestId = insertRequest(famedenRequestDetail);
			} catch (Exception e) {
				 logger.error(e.getMessage(), e);
			}
			
		}
		return null;
	}

	@Override
	public Object populateResponse(Object obj) {
		FamedenForgotPasswordResponse response = null;
		RegistrationDTO dto = (RegistrationDTO) obj;
		if (dto != null) {
			response = new FamedenForgotPasswordResponse();
			response.setStatus(dto.getStatus());
			response.setTransactionId(dto.getTransactionId());
			response.setErrorMessage(dto.getMessage());
		}
		return response;
	}

	@Override
	public int insertRequest(FamedenRequestDetail famedenRequestDetail)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object populateRequest(Object obj) {
		FamedenRequestDetail famedenRequestDetail = null;
		FamedenRequest famedenRequest = null;
		ForgotPasswordDTO dto = (ForgotPasswordDTO) obj;
		if (dto != null) {
			famedenRequest = new FamedenRequest();
			famedenRequest.setCustomerIP(dto.getCustomerIP());
			famedenRequest.setRequestStatus(GlobalConstants.IN_PROCESS);
			famedenRequest.setRequestType(GlobalConstants.ForgotPassword_REQUEST);
			famedenRequest.setRequestUser(dto.getEmailAddress());
			famedenRequest.setFamedenUserId(0);
			famedenRequestDetail = new FamedenRequestDetail();
			famedenRequestDetail.setFamedenRequest(famedenRequest);
		}
		return famedenRequestDetail;
	}

}
