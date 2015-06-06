package com.jp.asilla.social.listening.dao.ibatis;

import org.apache.ibatis.session.SqlSession;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLJobDao;
import com.jp.asilla.social.listening.dao.SLPlaceDao;
import com.jp.asilla.social.listening.dao.SLStatusDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLDaoFactoryImpl extends SLDaoFactory {
	
	@Override
	public SLTransaction openTransaction() throws SLDaoException {
		try {
			SqlSession session = SLConnectionFactory.getSessionFactory().openSession();
			return new SLTransactionImpl(session);
		} catch (SLDaoException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public SLJobDao getJobDao() {
		return new SLJobDaoImpl();
	}
	
	@Override
	public SLStatusDao getStatusDao() {
		return new SLStatusDaoImpl();
	}
	
	@Override
	public SLPlaceDao getPlaceDao() {
		return new SLPlaceDaoImpl();
	}
}
