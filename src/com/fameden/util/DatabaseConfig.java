package com.fameden.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {

	static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure()
						.buildSessionFactory();
			} catch (Throwable ex) {
				logger.info("Exception in HibernateUtil");
				logger.error(null, ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}
}
