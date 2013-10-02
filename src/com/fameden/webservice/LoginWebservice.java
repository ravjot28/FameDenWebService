package com.fameden.webservice;

import javax.jws.WebService;

import com.fameden.dto.LoginDTO;
import com.fameden.model.FamedenLoginModel;
import com.fameden.model.FamedenUserProfileModel;
import com.fameden.webservice.contracts.ILoginWS;


@WebService(endpointInterface = "com.fameden.webservice.contracts.ILoginWS", 
serviceName =  "Login", portName = "LoginPort")
public class LoginWebservice implements ILoginWS {

	@Override
	public FamedenUserProfileModel login(FamedenLoginModel loginModel) {
		
		FamedenUserProfileModel up = new FamedenUserProfileModel();
		up.setUserName("User name not set"); 
		if (loginModel != null) {
			
			LoginDTO loginDTO = new LoginDTO();
			
			loginDTO.setUserID(loginModel.getUserID());
			loginDTO.setPassword(loginModel.getPassword());
			loginDTO.setLoginMode(loginModel.getLoginMode());
			
			//TODO Set Request DTO and RequestDetailDTO
			up.setUserName("User name is user id" + loginDTO.getUserID());
			
			//TODO Call request generator method passing DTO 
			
			
			
		}else {
			//TODO Exception Raise code 
		}
		return up;
	}

}
