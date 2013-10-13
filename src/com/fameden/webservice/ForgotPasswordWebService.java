package com.fameden.webservice;

import javax.jws.WebService;

import com.fameden.constants.GlobalConstants;
import com.fameden.dto.ForgotPasswordDTO;
import com.fameden.dto.RegistrationDTO;
import com.fameden.model.FamedenForgotPasswordRequest;
import com.fameden.model.FamedenForgotPasswordResponse;
import com.fameden.model.FamedenRegistrationResponse;
import com.fameden.service.ForgotPasswordService;
import com.fameden.webservice.contracts.ICommon;
import com.fameden.webservice.contracts.IForgotPassword;

@WebService(endpointInterface = "com.fameden.webservice.contracts.IForgotPassword", serviceName = "ForgotPassword", portName = "ForgotPasswordPort")
public class ForgotPasswordWebService implements IForgotPassword, ICommon {

	@Override
	public Object populateDTO(Object obj) {
		FamedenForgotPasswordRequest request =(FamedenForgotPasswordRequest) obj;
		ForgotPasswordDTO dto = null;
		
		if (request != null) {
			dto = new ForgotPasswordDTO();
			dto.setCustomerIP(request.getCustomerIP());
			dto.setEmailAddress(request.getEmailAddress());
			dto.setMessage(null);
			dto.setRequestType(request.getRequestType());
			dto.setStatus(GlobalConstants.IN_PROCESS);
			dto.setTransactionId(null);
			
		}
		return dto;
	}

	@Override
	public FamedenForgotPasswordResponse forgotPassword(FamedenForgotPasswordResponse Request) {
		
		ForgotPasswordService service = new ForgotPasswordService();
		FamedenForgotPasswordResponse response = null;
		
		ForgotPasswordDTO dto = (ForgotPasswordDTO) this.populateDTO(Request);
		
		if (dto !=null) {
			response = (FamedenForgotPasswordResponse)service.processRequest(dto);
		}else {
			dto = new ForgotPasswordDTO();
			dto.setStatus(GlobalConstants.FAILURE);
			dto.setMessage(GlobalConstants.GENERIC_ERROR_MESSAGE);
			response = (FamedenForgotPasswordResponse) service
					.populateResponse(dto);
		}
		
		
		return null;
	}

}
