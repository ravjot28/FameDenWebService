package com.fameden.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenLogin;
import com.fameden.bean.FamedenRequest;
import com.fameden.constants.GlobalConstants;
import com.fameden.constants.LoginConstants;
import com.fameden.dao.LoginDAO;
import com.fameden.dto.LoginDTO;
import com.fameden.exception.LoginValidationException;
import com.fameden.model.FamedenUserProfileModel;
import com.fameden.util.CommonValidations;
import com.fameden.util.SaltTextEncryption;

public class LoginService {
	
	Logger logger = LoggerFactory.getLogger(LoginService.class);
		
	private boolean validate(Object obj) throws LoginValidationException{
		
		LoginDTO loginDTO = (LoginDTO) obj;
		boolean result = false ;
		
		if (!CommonValidations.isStringEmpty(loginDTO.getEmailAddress())) {
			if (!CommonValidations.isStringEmpty(loginDTO.getPassword())){
				if (!CommonValidations.isStringEmpty(loginDTO.getLoginMode())) {
					result = true;
				}else {
					throw new LoginValidationException(LoginConstants.invalidLoginModeMessage);
				}
			}else {
				throw new LoginValidationException(LoginConstants.invalidPasswordMessage);
			}

		}else {
			throw new LoginValidationException(LoginConstants.invalidUserIDMessage);
			
		}
		/*SaltTextEncryption ste = new SaltTextEncryption();
		
		String hash = ste.createHash("ravjot28");
		
		System.out.println(hash);
		
		System.out.println(ste.validatePassword("ravjot2.8", hash));*/
		
		return result;
	}
	
	public Object processRequest(Object obj) throws NoSuchAlgorithmException, InvalidKeySpecException {

		LoginDTO loginDTO = (LoginDTO) obj;
		boolean validation = false;
		LoginDAO loginDAO  = null;
		
		String errorMessage = null;
		try {
			validation = validate(loginDTO);
			
		} catch (LoginValidationException e) {
			logger.error(e.getMessage(), e);
			errorMessage = e.getMessage();
		}
		
		if (!validation && errorMessage != null) {
			loginDTO.setStatus(GlobalConstants.FAILURE);
			loginDTO.setMessage(errorMessage);
			
		}else {
			loginDAO = new LoginDAO(); 
			loginDAO.authenticateAndFetchUserProfile(loginDTO);
			
			
			
		}
		
		
		return loginDAO;
	}
	
	
/*	private FamedenLogin populateLoginBean(LoginDTO loginDTO){
		
		FamedenLogin loginBean = new FamedenLogin();
		FamedenRequest requestBean = new FamedenRequest();
		Date requestDate ;
		
		loginBean.setUserID(loginDTO.getUserID());
		loginBean.setPassword(loginDTO.getPassword());
		loginBean.setLoginMode(loginDTO.getLoginMode());
		requestBean.setCustomerIP(loginDTO.getCustomerIP());
		//requestBean.setRequestDate(loginDTO.get);
		
		return loginBean;
		
	}
*/
}
