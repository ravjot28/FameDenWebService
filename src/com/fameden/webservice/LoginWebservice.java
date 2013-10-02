package com.fameden.webservice;

import javax.jws.WebService;

import com.fameden.constants.GlobalConstants;
import com.fameden.dto.LoginDTO;
import com.fameden.model.FamedenLoginModel;
import com.fameden.model.FamedenUserProfileModel;
import com.fameden.service.LoginService;
import com.fameden.webservice.contracts.ICommon;
import com.fameden.webservice.contracts.ILoginWS;

@WebService(endpointInterface = "com.fameden.webservice.contracts.ILoginWS", serviceName = "LoginService", portName = "LoginPort")
public class LoginWebservice implements ILoginWS, ICommon {

	@Override
	public FamedenUserProfileModel login(FamedenLoginModel loginModel) {

		FamedenUserProfileModel userProfile = null;


			LoginDTO loginDTO = (LoginDTO) this.populateDTO(loginModel);

			
			LoginService loginService = new LoginService();
			
			//userProfile = (FamedenUserProfileModel)loginService.processRequest(loginDTO);
			
			//TODO: Set user profile model object.

		
		return userProfile;
	}

	@Override
	public Object populateDTO(Object obj) {

		LoginDTO loginDTO = new LoginDTO();
		FamedenLoginModel loginModel = (FamedenLoginModel) obj;
		
		loginDTO.setCustomerIP(loginModel.getCustomerIP());
		loginDTO.setEmailAddress(loginModel.getEmailAddress());
		loginDTO.setEndUser(loginModel.getEndUser());
		loginDTO.setMessage(null);
		loginDTO.setRequestType(loginModel.getRequestType());
		loginDTO.setUserID(loginModel.getEndUser());
		loginDTO.setPassword(loginModel.getPassword());
		loginDTO.setLoginMode(loginModel.getLoginMode());
		loginDTO.setStatus(GlobalConstants.IN_PROCESS);
		loginDTO.setTransactionId(null);
		
		return loginDTO;
	}

}
