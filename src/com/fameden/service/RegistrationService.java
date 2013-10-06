package com.fameden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.GlobalConstants;
import com.fameden.constants.RegistrationConstants;
import com.fameden.dao.CommonRequestOperationDAO;
import com.fameden.dao.CommonUserOperation;
import com.fameden.dao.UserRegistrationDAO;
import com.fameden.dto.RegistrationDTO;
import com.fameden.exception.RegistrationValidationException;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.util.CommonValidations;
import com.fameden.util.SendMail;

public class RegistrationService implements ICommonService {

	Logger logger = LoggerFactory.getLogger(RegistrationService.class);

	public boolean validate(Object obj) throws RegistrationValidationException {
		RegistrationDTO dto = (RegistrationDTO) obj;
		boolean result = false;
		if (!CommonValidations.isStringEmpty(dto.getEmailAddress())
				&& CommonValidations.isValidEmailAddress(dto.getEmailAddress())) {
			if (!CommonValidations.isStringEmpty(dto.getFullName())) {
				if (!CommonValidations.isStringEmpty(dto.getPassword())) {
					CommonUserOperation commonUserOperation = new CommonUserOperation();
					if (commonUserOperation.searchByEmailId(dto
							.getEmailAddress()) == null) {
						result = true;
					} else {
						throw new RegistrationValidationException(
								RegistrationConstants.userAlreadyExisitsMessage);
					}
				} else {
					throw new RegistrationValidationException(
							RegistrationConstants.invalidPasswordMessage);
				}
			} else {
				throw new RegistrationValidationException(
						RegistrationConstants.invalidFullNameMessage);
			}

		} else {
			throw new RegistrationValidationException(
					RegistrationConstants.invalidEmailAddressMessage);
		}

		return result;
	}

	public FamedenRegistrationResponse processRequest(Object obj) {

		RegistrationDTO dto = (RegistrationDTO) obj;
		boolean validation = false;
		int requestId = -1;
		String errorMessage = null;
		try {
			validation = validate(dto);
		} catch (RegistrationValidationException e) {
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
				logger.error(e.getMessage(), e);
			}
			if (requestId != -1) {
				UserRegistrationDAO userRegistrationDAO = new UserRegistrationDAO();
				String verificationCode = null;

				try {
					verificationCode = userRegistrationDAO.registerUser(dto);
				} catch (Exception e) {
					dto.setStatus(GlobalConstants.FAILURE);
					dto.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
				}
				if (verificationCode != null) {
					String message = "Hi "
							+ dto.getFullName()
							+ "\n\nPlease find below the URL to verify your email address for further communications\n\n"
							+ "http://localhost:8070/FamedenVerificationProject/UserEmailVerification?userEmailVerification="
							+ verificationCode
							+ "\n\nThanks and Regards,\nFameDen Inc.";
					String to[] = { dto.getEmailAddress() };
					SendMail sm = new SendMail(
							"Please Verify Your Email Address", message, null,
							to);
					sm.send();

					dto.setStatus(GlobalConstants.SUCCESS);
				}
			} else {
				dto.setStatus(GlobalConstants.FAILURE);
				dto.setMessage(errorMessage);
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
		
		FamedenRegistrationResponse response = (FamedenRegistrationResponse) populateResponse(dto);
		
		return response;
	}

	public Object populateResponse(Object obj) {
		FamedenRegistrationResponse response = null;
		RegistrationDTO dto = (RegistrationDTO) obj;
		if (dto != null) {
			response = new FamedenRegistrationResponse();
			response.setStatus(dto.getStatus());
			response.setTransactionId(dto.getTransactionId());
			response.setErrorMessage(dto.getMessage());
		}
		return response;
	}

	public Object populateRequest(Object obj) {
		FamedenRequestDetail famedenRequestDetail = null;
		FamedenRequest famedenRequest = null;
		RegistrationDTO dto = (RegistrationDTO) obj;
		if (dto != null) {
			famedenRequest = new FamedenRequest();
			famedenRequest.setCustomerIP(dto.getCustomerIP());
			famedenRequest.setRequestStatus(GlobalConstants.IN_PROCESS);
			famedenRequest.setRequestType(GlobalConstants.REGISTRATION_REQUEST);
			famedenRequest.setRequestUser(dto.getEmailAddress());
			famedenRequest.setFamedenUserId(0);
			famedenRequestDetail = new FamedenRequestDetail();
			famedenRequestDetail.setFamedenRequest(famedenRequest);
		}
		return famedenRequestDetail;
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

}
