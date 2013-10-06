package com.fameden.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenRequest;
import com.fameden.bean.FamedenRequestDetail;
import com.fameden.util.DatabaseConfig;

public class CommonRequestOperationDAO implements Serializable {
	private static final long serialVersionUID = 8562224930520844251L;

	Logger logger = LoggerFactory.getLogger(CommonRequestOperationDAO.class);

	public int insertRequest(FamedenRequestDetail frd) throws Exception {
		int requestId = -1;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			session.save(frd);

			session.getTransaction().commit();

			requestId = frd.getFamedenRequest().getRequestID();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return requestId;
	}

	public boolean updateRequestStatus(int requestId, String status)
			throws Exception {
		boolean result = false;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			FamedenRequest request = (FamedenRequest) session.get(
					FamedenRequest.class, new Integer(requestId));

			request.setRequestStatus(status);
			request.setRequestUpdateDate(new Date(Calendar.getInstance()
					.getTimeInMillis()));

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return result;
	}

	public FamedenRequestDetail getRequest(int requestId) throws Exception {
		FamedenRequestDetail frd = null;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(FamedenRequestDetail.class);

			crit.createAlias("famedenRequest", "frd");
			Criterion requestIDRestriction = Restrictions.eq("frd.requestID",
					requestId);
			crit.add(requestIDRestriction);
			List<FamedenRequestDetail> famedenRequestDetailList = ((List<FamedenRequestDetail>) crit
					.list());

			if (famedenRequestDetailList != null
					&& famedenRequestDetailList.size() > 0) {
				frd = famedenRequestDetailList.get(0);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return frd;
	}

}
