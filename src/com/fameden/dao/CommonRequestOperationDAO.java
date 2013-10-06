package com.fameden.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.constants.GlobalConstants;
import com.fameden.util.DatabaseConfig;

public class CommonRequestOperationDAO implements Serializable, ICommonDAO {
	private static final long serialVersionUID = 8562224930520844251L;

	public int insertRequest(FamedenRequestDetail frd) {
		int requestId = -1;

		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		session.save(frd);

		session.getTransaction().commit();

		return requestId;
	}

	public boolean updateRequestStatus(int requestId, String status) {
		boolean result = false;
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		FamedenRequest request = (FamedenRequest) session.get(
				FamedenRequest.class, new Integer(requestId));

		request.setRequestStatus(status);
		request.setRequestUpdateDate(new Date(Calendar.getInstance()
				.getTimeInMillis()));
		
		session.getTransaction().commit();
		return result;
	}

	public FamedenRequestDetail getRequest(int requestId) {
		FamedenRequestDetail frd = null;
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		Criteria crit = session.createCriteria(FamedenRequestDetail.class);

		crit.createAlias("famedenRequest", "frd");
		Criterion requestIDRestriction = Restrictions
				.eq("frd.requestID", requestId);
		crit.add(requestIDRestriction);
		List<FamedenRequestDetail> famedenRequestDetailList = ((List<FamedenRequestDetail>) crit
				.list());

		if (famedenRequestDetailList != null
				&& famedenRequestDetailList.size() > 0) {
			frd = famedenRequestDetailList.get(0);
		}

		session.getTransaction().commit();
		return frd;
	}

	public static void main(String[] args) {
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

	@Override
	public Object populateDAOFromDTO(Object dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
