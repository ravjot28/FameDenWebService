package com.fameden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.constants.GlobalConstants;
import com.fameden.constants.RegistrationConstants;
import com.fameden.dto.RegistrationDTO;
import com.fameden.exception.RegistrationValidationException;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.util.CommonValidations;

public class RegistrationService {

	Logger logger = LoggerFactory.getLogger(RegistrationService.class);

	public boolean validate(Object obj) throws RegistrationValidationException {
		RegistrationDTO dto = (RegistrationDTO) obj;
		boolean result = false;
		if (!CommonValidations.isStringEmpty(dto.getEmailAddress())
				&& CommonValidations.isValidEmailAddress(dto.getEmailAddress())) {

			if (!CommonValidations.isStringEmpty(dto.getFullName())) {
				if (!CommonValidations.isStringEmpty(dto.getPassword())) {
					result = true;
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

	public void processRequest(Object obj) {

		RegistrationDTO dto = (RegistrationDTO) obj;
		boolean validation = false;
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

		}

	}

	public Object populateResponse(Object obj) {
		FamedenRegistrationResponse response = null;
		RegistrationDTO dto = (RegistrationDTO) obj;
		response = new FamedenRegistrationResponse();
		response.setStatus(dto.getStatus());
		response.setTransactionId(dto.getTransactionId());
		response.setErrorMessage(dto.getMessage());
		return response;
	}

}
