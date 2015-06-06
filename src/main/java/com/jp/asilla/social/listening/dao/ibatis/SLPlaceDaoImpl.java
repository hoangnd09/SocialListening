package com.jp.asilla.social.listening.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jp.asilla.social.listening.dao.SLPlaceDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLPlaceDaoImpl implements SLPlaceDao {

	@Override
	public SLPlace get(String id) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("placeId", id);
			
			return session.selectOne("places.selectByID", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<SLPlace> select(int offset, int limit) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("limit", limit);
			param.put("offset", offset);
			
			return session.selectList("places.select", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public int insert(SLPlace model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int insert = session.insert("places.insert", model);
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
	public int insert(SLPlace model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.insert("places.insert", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int insert(List<SLPlace> models) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("models", models);
			
			int insert = session.insert("places.insertBatch", param);
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
	public int insertOrUpdate(SLPlace model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int insert = session.insert("places.insertOrUpdate", model);
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
	public int insertOrUpdate(SLPlace model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.insert("places.insertOrUpdate", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int insert(List<SLPlace> models, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("models", models);
			
			SqlSession session = transaction.getSession();
			return session.insert("places.insertBatch", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int update(SLPlace model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int update = session.update("places.update", model);
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
	public int update(SLPlace model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.update("places.update", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int delete(String id) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			int delete = session.delete("places.delete", param);
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
	public int delete(String id, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("placeId", id);
			
			SqlSession session = transaction.getSession();
			return session.delete("places.delete", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
}
