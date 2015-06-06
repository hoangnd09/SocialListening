package com.jp.asilla.social.listening.dao;

import com.jp.asilla.social.listening.SLSetting;
import com.jp.asilla.social.listening.exception.SLDaoException;


public abstract class SLDaoFactory {
	
	private static SLDaoFactory daoFactory;
	
	public static SLDaoFactory getInstance() {
		if (daoFactory == null) {
			String implement = SLSetting.getSetting(SLSetting.DAO_IMPLEMENT, "solr");
			if ("solr".equalsIgnoreCase(implement)) {
				daoFactory = new com.jp.asilla.social.listening.dao.solr.SLDaoFactoryImpl();
			} else {
				daoFactory = new com.jp.asilla.social.listening.dao.ibatis.SLDaoFactoryImpl();
			}
		}
		return daoFactory;
	}
	
	public abstract SLTransaction openTransaction() throws SLDaoException;
	
	public abstract SLJobDao getJobDao();
	
	public abstract SLStatusDao getStatusDao();
	
	public abstract SLPlaceDao getPlaceDao();
	
}
