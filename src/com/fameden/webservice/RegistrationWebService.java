package com.fameden.webservice;

import javax.jws.WebService;

import com.fameden.constants.GlobalConstants;
import com.fameden.dto.RegistrationDTO;
import com.fameden.model.FamedenRegistrationRequest;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.service.RegistrationService;
import com.fameden.webservice.contracts.ICommon;
import com.fameden.webservice.contracts.IRegistrationWS;

@WebService(endpointInterface = "com.fameden.webservice.contracts.IRegistrationWS", serviceName = "registrationService", portName = "RegistrationPort")
public class RegistrationWebService implements IRegistrationWS,ICommon {

	@Override
	public FamedenRegistrationResponse registerUser(
			FamedenRegistrationRequest request) {

		FamedenRegistrationResponse frr = null;
		
		RegistrationDTO dto = (RegistrationDTO) populateDTO(request);
		
		RegistrationService service = new RegistrationService();

		service.processRequest(dto);
		return frr;

	}

	@Override
	public Object populateDTO(Object obj) {
		RegistrationDTO dto = null;
		FamedenRegistrationRequest request = (FamedenRegistrationRequest) obj;
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
		return dto;
	}

}
