package com.jp.asilla.social.listening.dao.ibatis;

import org.apache.ibatis.session.SqlSession;

import com.jp.asilla.social.listening.dao.SLTransaction;

public class SLTransactionImpl implements SLTransaction {
	private SqlSession session;

	public SLTransactionImpl(SqlSession session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSession() {
		return (T) session;
	}
	
	@Override
	public void close() {
		if (session != null) {
			session.close();
			session = null;
		}
	}

	@Override
	public void rollback() {
		if (session != null) {
			session.rollback();
		}
	}
	
	@Override
	public void commit() {
		if (session != null) {
			session.commit();
		}
	}

}
