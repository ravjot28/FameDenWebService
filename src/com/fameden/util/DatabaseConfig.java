package com.fameden.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {
	
	static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	public static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory;
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			logger.info("Exception in HibernateUtil");
			logger.error(null, ex);
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
	}
}
