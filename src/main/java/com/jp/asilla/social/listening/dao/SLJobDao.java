package com.jp.asilla.social.listening.dao;

import java.util.List;

import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.exception.SLDaoException;

public interface SLJobDao {
	public SLJob get(String jobId) throws SLDaoException;
	
	public List<SLJob> select() throws SLDaoException;
	
	public List<SLJob> select(int offset, int limit) throws SLDaoException;
	
	public int insert(SLJob model) throws SLDaoException;
	
	public int insert(SLJob model, SLTransaction transaction) throws SLDaoException;
	
	public int update(SLJob model) throws SLDaoException;
	
	public int update(SLJob model, SLTransaction transaction) throws SLDaoException;
	
	public int delete(String jobId) throws SLDaoException;
	
	public int delete(String jobId, SLTransaction transaction) throws SLDaoException;

	public int insertOrUpdate(SLJob job, SLTransaction transaction) throws SLDaoException;

	public int updateState(String jobID, String state, SLTransaction transaction) throws SLDaoException;

	
}
