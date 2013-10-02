package com.fameden.webservice.contracts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    
    @WebMethod(operationName = "LoginAndGetUserProfile")
    @WebResult(name = "LoginResponse")
    public FamedenUserProfileModel login(@WebParam(name="LoginRequest")FamedenLoginModel loginModel);
	
	
}
