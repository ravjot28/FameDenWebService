package com.fameden.webservice.contracts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.fameden.model.FamedenForgotPasswordResponse;



@WebService(name = "ForGotServiceContract", targetNamespace = "http://www.fameden.com/")
public interface IForgotPassword {

	/**
     * ForgotPasswordService - ForgotPassword sends a verification email 
     * then resets the password for the email-id provided.
     * @param ForgotPasswordRequest (containing Email-Id, Verification Code and Request details.)
     * @return
     *     returns com.fameden.model.FamedenForgotPasswordResponse
     */
    
    
    @WebMethod(operationName = "ForgotPassword")
    @WebResult(name = "ForgotPasswordResponse")
    public FamedenForgotPasswordResponse forgotPassword(@WebParam(name="ForgotPasswordRequest")FamedenForgotPasswordResponse Request);
	
	
}
