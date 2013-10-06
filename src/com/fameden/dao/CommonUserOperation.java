package com.fameden.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fameden.bean.FamedenUser;
import com.fameden.util.DatabaseConfig;

public class CommonUserOperation implements Serializable {

	private static final long serialVersionUID = -8046989953394977117L;

	Logger logger = LoggerFactory.getLogger(CommonUserOperation.class);

	public FamedenUser searchByEmailId(String emailId) {
		FamedenUser user = null;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(FamedenUser.class);
			Criterion emailAddressRestriction = Restrictions.eq("emailAddress",
					emailId);
			crit.add(emailAddressRestriction);
			List<FamedenUser> famedenUserList = ((List<FamedenUser>) crit
					.list());

			if (famedenUserList != null && famedenUserList.size() > 0) {
				user = famedenUserList.get(0);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return user;
	}

}
