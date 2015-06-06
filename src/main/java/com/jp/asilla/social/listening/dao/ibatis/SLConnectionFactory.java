package com.jp.asilla.social.listening.dao.ibatis;

import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLConnectionFactory {
	private static String environment = null;
	private static SqlSessionFactory sessionFactory;
	private static final String DATABASE_CONFIGURE_PATH = "com/jp/asilla/social/listening/mysql/database_config.xml";
	
	public static SqlSessionFactory getSessionFactory() throws SLDaoException {
		if (sessionFactory == null) {
			try {
				sessionFactory = new SqlSessionFactoryBuilder().build(
						Resources.getResourceAsReader(DATABASE_CONFIGURE_PATH), 
						getEnvironment());
			} catch (IOException e) {
				throw new SLDaoException(e);
			}
		}
		return sessionFactory;
	}
	
	private static String getEnvironment() throws SLDaoException {
		if (environment == null || environment.trim().length() == 0) {
			try {
				Properties prop = new Properties();
				prop.load(SLConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties"));
				environment = prop.getProperty("database.environment");
			} catch (IOException e) {
				throw new SLDaoException(e);
			}
		}
		return environment;
	}
}
