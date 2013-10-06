package com.fameden.test.registration;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.GlobalConstants;
import com.fameden.dao.CommonRequestOperationDAO;
import com.fameden.dao.CommonUserOperation;
import com.fameden.dao.UserRegistrationDAO;
import com.fameden.dto.RegistrationDTO;
import com.fameden.model.FamedenRegistrationRequest;
import com.fameden.webservice.RegistrationWebService;

public class Test {
	public static void main(String[] args) {

	}

	public void testRegistrationWebService() {
		RegistrationWebService r = new RegistrationWebService();
		FamedenRegistrationRequest request = new FamedenRegistrationRequest();
		request.setCustomerIP("10.0.0.10");
		request.setEmailAddress("arora.puneet777@gmail.com");
		request.setFullName("Puneet Arora");
		request.setPassword("1234");
		request.setPrivateToken("1");
		request.setPublicToken("2");
		request.setRegistrationType("TWITTER");
		request.setRequestType(GlobalConstants.REGISTRATION_REQUEST);
		request.setAlternateEmailAddress("ravjot.singh.28@gmail.com");
		r.registerUser(request);
	}

	public void testCommonRequestOperationDAO() throws Exception {

		CommonRequestOperationDAO cro = new CommonRequestOperationDAO();

		FamedenRequest fr = new FamedenRequest();
		FamedenRequestDetail frd = new FamedenRequestDetail();

		/*
		 * fr.setCustomerIP("192.168.1.0"); fr.setRequestDate(new
		 * Date(Calendar.getInstance() .getTimeInMillis()));
		 * fr.setRequestStatus(GlobalConstants.IN_PROCESS);
		 * fr.setRequestType("Test"); fr.setRequestUser("ravjot28@gmail.com");
		 * 
		 * frd.setCustomerCost(100.2); frd.setFamedenRequest(fr);
		 * frd.setItemID("101"); frd.setItemName("TestItem");
		 * frd.setItemType("TestType"); frd.setPaymentMode("TestMode");
		 * 
		 * cro.insertRequest(frd);
		 */

		cro.updateRequestStatus(13, GlobalConstants.SUCCESS);

		frd = cro.getRequest(13);

		System.out.println(frd.getCustomerCost());
		System.out.println(frd.getItemID());
		System.out.println(frd.getItemName());
		System.out.println(frd.getItemType());
		System.out.println(frd.getPaymentMode());
		System.out.println(frd.getRequestDetailID());
		System.out.println(frd.getFamedenRequest().getCustomerIP());
		System.out.println(frd.getFamedenRequest().getRequestID());
		System.out.println(frd.getFamedenRequest().getRequestStatus());
		System.out.println(frd.getFamedenRequest().getRequestType());
		System.out.println(frd.getFamedenRequest().getRequestUser());
		System.out.println(frd.getFamedenRequest().getRequestDate());
		System.out.println(frd.getFamedenRequest().getRequestUpdateDate());

	}

	public void testCommonUserOperation() {

		CommonUserOperation co = new CommonUserOperation();
		co.searchByEmailId("ravjot28@gmail.com");

	}

	public void testUserRegistrationDAO() throws Exception {

		RegistrationDTO dto = new RegistrationDTO();
		dto.setCustomerIP("192.168.1.1");
		dto.setEmailAddress("ravjot28@gmail.com");
		dto.setFullName("Ravjot Singh");
		dto.setPassword("ravjot123");
		dto.setPrivateToken("ravjottoken1");
		dto.setPublicToken("ravjottoken2");
		dto.setRegistrationType("facebook");
		new UserRegistrationDAO().registerUser(dto);

	}

}
