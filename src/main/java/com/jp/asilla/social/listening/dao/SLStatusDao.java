package com.jp.asilla.social.listening.dao;

import java.util.List;

import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.exception.SLDaoException;

public interface SLStatusDao {

	public SLStatus get(long id) throws SLDaoException;
	
	public List<SLStatus> select(int offset, int limit) throws SLDaoException;
	
	public int insert(SLStatus model) throws SLDaoException;
	
	public int insert(SLStatus model, SLTransaction transaction) throws SLDaoException;
	
	public int insertOrUpdate(SLStatus model) throws SLDaoException;
	
	public int insertOrUpdate(SLStatus model, SLTransaction transaction) throws SLDaoException;
	
	public int insert(List<SLStatus> models) throws SLDaoException;
	
	public int insert(List<SLStatus> models, SLTransaction transaction) throws SLDaoException;
	
	public int update(SLStatus model) throws SLDaoException;
	
	public int update(SLStatus model, SLTransaction transaction) throws SLDaoException;
	
	public int delete(long id) throws SLDaoException ;
	
	public int delete(long id, SLTransaction transaction) throws SLDaoException ;

}
