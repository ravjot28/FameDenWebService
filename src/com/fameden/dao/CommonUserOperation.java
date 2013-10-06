package com.fameden.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fameden.bean.FamedenRequestDetail;
import com.fameden.bean.FamedenUser;
import com.fameden.util.DatabaseConfig;

public class CommonUserOperation  implements Serializable, ICommonDAO {
	
	private static final long serialVersionUID = -8046989953394977117L;

	public  FamedenUser searchByEmailId(String emailId){
		FamedenUser user =null;
		
		Session session = DatabaseConfig.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		Criteria crit = session.createCriteria(FamedenUser.class);
		Criterion emailAddressRestriction = Restrictions
				.eq("emailAddress", emailId);
		crit.add(emailAddressRestriction);
		List<FamedenUser> famedenUserList = ((List<FamedenUser>) crit
				.list());

		if (famedenUserList != null
				&& famedenUserList.size() > 0) {
			user = famedenUserList.get(0);
		}

		session.getTransaction().commit();
		
		return user;
	}
	
	public static void main(String[] args) {
		CommonUserOperation co = new CommonUserOperation();
		co.searchByEmailId("ravjot28@gmail.com");
	}

	@Override
	public Object populateDAOFromDTO(Object dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
