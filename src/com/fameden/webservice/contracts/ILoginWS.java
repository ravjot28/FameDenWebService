package com.fameden.webservice.contracts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.fameden.model.FamedenLoginRequest;
import com.fameden.model.FamedenLoginResponse;


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
    public FamedenLoginResponse login(@WebParam(name="LoginRequest")FamedenLoginRequest loginRequest);
	
    @WebMethod(operationName = "ForgotPassword")
    @WebResult(name = "ForgotPasswordResponse")
    public FamedenLoginResponse forgotPassword(@WebParam(name="ForgotPasswordRequest")FamedenLoginRequest loginRequest);
	
	
}
