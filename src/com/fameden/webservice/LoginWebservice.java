package com.fameden.webservice;

import javax.jws.WebService;

import com.fameden.constants.GlobalConstants;
import com.fameden.dto.LoginRequestDTO;
import com.fameden.model.FamedenLoginRequest;
import com.fameden.model.FamedenLoginResponse;
import com.fameden.service.LoginService;
import com.fameden.webservice.contracts.ICommon;
import com.fameden.webservice.contracts.ILoginWS;

@WebService(endpointInterface = "com.fameden.webservice.contracts.ILoginWS", serviceName = "LoginService", portName = "LoginPort")
public class LoginWebservice implements ILoginWS, ICommon {

	@Override
	public FamedenLoginResponse login(FamedenLoginRequest loginRequestModel) {

		FamedenLoginResponse userProfile = null;


			LoginRequestDTO loginDTO = (LoginRequestDTO) this.populateDTO(loginRequestModel);			
			LoginService loginService = new LoginService();
			
			userProfile = (FamedenLoginResponse)loginService.processRequest(loginDTO);
			
			//TODO: Set user profile model object.

		return userProfile;
	}

	@Override
	public Object populateDTO(Object obj) {

		LoginRequestDTO loginDTO = new LoginRequestDTO();
		FamedenLoginRequest loginModel = (FamedenLoginRequest) obj;
		
		loginDTO.setCustomerIP(loginModel.getCustomerIP());
		loginDTO.setEmailAddress(loginModel.getEmailAddress());
		loginDTO.setMessage(null);
		loginDTO.setRequestType(loginModel.getRequestType());
		loginDTO.setPassword(loginModel.getPassword());
		loginDTO.setLoginMode(loginModel.getLoginMode());
		loginDTO.setStatus(GlobalConstants.IN_PROCESS);
		loginDTO.setTransactionId(null);

		return loginDTO;
	}
	



	@Override
	public FamedenLoginResponse forgotPassword(FamedenLoginRequest loginRequest) {
		
		
		
		return null;
	}
	

}
