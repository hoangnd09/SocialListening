package com.jp.asilla.social.listening.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jp.asilla.social.listening.dao.SLJobDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLJobDaoImpl implements SLJobDao {
	
	@Override
	public SLJob get(String jobId) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("jobId", jobId);
			
			return session.selectOne("job.selectByID", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<SLJob> select() throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			return session.selectList("job.select", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<SLJob> select(int offset, int limit) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("limit", limit);
			param.put("offset", offset);
			
			return session.selectList("job.select", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public int insert(SLJob model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int insert = session.insert("job.insert", model);
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
	public int insert(SLJob model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.insert("job.insert", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int update(SLJob model) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			int update = session.update("job.update", model);
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
	public int update(SLJob model, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.update("job.update", model);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public int delete(String jobId) throws SLDaoException {
		SqlSessionFactory sqlSessionFactory = SLConnectionFactory.getSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("jobId", jobId);
			int delete = session.delete("job.delete", param);
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
	public int delete(String jobId, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("jobId", jobId);
			
			SqlSession session = transaction.getSession();
			return session.delete("job.delete", param);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insertOrUpdate(SLJob job, SLTransaction transaction) throws SLDaoException {
		try {
			SqlSession session = transaction.getSession();
			return session.update("job.insertOrUpdate", job);
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int updateState(String jobID, String state, SLTransaction transaction) throws SLDaoException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("jobId", jobID);
			param.put("state", state);
			
			SqlSession session = transaction.getSession();
			return session.update("job.updateState", param);
			
		} catch (PersistenceException e) {
			throw new SLDaoException(e);
		}
	}

}
