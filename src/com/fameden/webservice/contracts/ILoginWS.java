package com.fameden.webservice.contracts;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.fameden.model.FamedenLoginModel;
import com.fameden.model.FamedenUserProfileModel;


@WebService(name = "LoginServiceContract", targetNamespace = "http://www.fameden.com/")
public interface ILoginWS {

	/**
     * LoginService - login authenticates user and returns user profile.
     * 
     * @param FamedenLoginModel (containing user id, encrypted pass, login mode and Request details.)
     * @return
     *     returns com.fameden.model.FamedenUserProfileModel
     */
    
    @WebMethod(operationName = "LoginAndGetUserProfile", action = "http://www.fameden.com/Login")
    @WebResult(name = "UserProfileResult", targetNamespace = "http://www.fameden.com/")
    @RequestWrapper(localName = "LoginAndGetUserProfile", targetNamespace = "http://www.fameden.com/")
    @ResponseWrapper(localName = "UserProfileResponse", targetNamespace =  "http://www.fameden.com/")
    public FamedenUserProfileModel login(FamedenLoginModel loginModel);
	
	
}
