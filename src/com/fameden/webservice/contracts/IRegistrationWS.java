package com.fameden.webservice.contracts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.fameden.model.FamedenRegistrationRequest;
import com.fameden.model.FamedenRegistrationResponse;

@WebService(name = "registrationService")
public interface IRegistrationWS  {

	@WebMethod(operationName = "registerUser")
	@WebResult(name = "registrationResponse")
	public FamedenRegistrationResponse registerUser(@WebParam(name="registrationRequest") FamedenRegistrationRequest request);

}