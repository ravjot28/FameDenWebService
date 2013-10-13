package com.fameden.webservice.registration;

import javax.jws.WebService;

import com.fameden.constants.GlobalConstants;
import com.fameden.dto.RegistrationDTO;
import com.fameden.model.FamedenRegistrationRequest;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.service.RegistrationService;
import com.fameden.webservice.contracts.ICommon;
import com.fameden.webservice.contracts.registration.IRegistrationWS;

@WebService(endpointInterface = "com.fameden.webservice.contracts.registration.IRegistrationWS", serviceName = "registrationService", portName = "RegistrationPort")
public class RegistrationWebService implements IRegistrationWS, ICommon {

	@Override
	public FamedenRegistrationResponse registerUser(
			FamedenRegistrationRequest request) {

		RegistrationService service = new RegistrationService();
		FamedenRegistrationResponse response = null;

		RegistrationDTO dto = (RegistrationDTO) populateDTO(request);
		if (dto != null) {
			response = service.processRequest(dto);
		} else {
			dto = new RegistrationDTO();
			dto.setStatus(GlobalConstants.FAILURE);
			dto.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
			response = (FamedenRegistrationResponse) service
					.populateResponse(dto);
		}

		return response;

	}

	@Override
	public Object populateDTO(Object obj) {
		RegistrationDTO dto = null;
		FamedenRegistrationRequest request = (FamedenRegistrationRequest) obj;
		if (request != null) {
			dto = new RegistrationDTO();
			dto.setCustomerIP(request.getCustomerIP());
			dto.setEmailAddress(request.getEmailAddress());
			dto.setFullName(request.getFullName());
			dto.setMessage(null);
			dto.setPassword(request.getPassword());
			dto.setPrivateToken(request.getPrivateToken());
			dto.setPublicToken(request.getPublicToken());
			dto.setRegistrationType(request.getRegistrationType());
			dto.setRequestType(request.getRequestType());
			dto.setStatus(GlobalConstants.IN_PROCESS);
			dto.setTransactionId(null);
			dto.setAlternateEmailAddress(request.getAlternateEmailAddress());
		}
		return dto;
	}

}
