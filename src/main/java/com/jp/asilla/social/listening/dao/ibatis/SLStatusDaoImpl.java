package com.jp.asilla.social.listening.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jp.asilla.social.listening.dao.SLStatusDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLStatusDaoImpl implements SLStatusDao{

	@Override
	public SLStatus get(long id) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("statusId", id);
			
			return session.selectOne("status.selectByID", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public List<SLStatus> select(int offset, int limit) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("limit", limit);
			param.put("offset", offset);
			
			return session.selectList("status.select", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(SLStatus model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int insert = session.insert("status.insert", model);
			session.commit();
			return insert;
		} catch (PersistenceException e) {
			session.rollback();
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(SLStatus model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.insert("status.insert", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insertOrUpdate(SLStatus model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int insert = session.insert("status.insertOrUpdate", model);
			session.commit();
			return insert;
		} catch (PersistenceException e) {
			session.rollback();
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public int insertOrUpdate(SLStatus model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.insert("status.insertOrUpdate", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insert(List<SLStatus> models) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("models", models);
			
			int insert = session.insert("status.insertBatch", param);
			session.commit();
			return insert;
		} catch (PersistenceException e) {
			session.rollback();
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(List<SLStatus> models, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("models", models);
			SqlSession session = transaction.getSession();
			return session.insert("status.insertBatch", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int update(SLStatus model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int update = session.update("status.update", model);
			session.commit();
			return update;
		} catch (PersistenceException e) {
			session.rollback();
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public int update(SLStatus model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.update("status.update", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int delete(long id) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			int delete = session.delete("status.delete", param);
			session.commit();
			return delete;
		} catch (PersistenceException e) {
			session.rollback();
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public int delete(long id, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("statusId", id);
			
			SqlSession session = transaction.getSession();
			return session.delete("status.delete", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
}
