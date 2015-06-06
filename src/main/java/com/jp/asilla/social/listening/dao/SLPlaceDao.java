package com.jp.asilla.social.listening.dao;

import java.util.List;

import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.exception.SLDaoException;

public interface SLPlaceDao {

	public SLPlace get(String id) throws SLDaoException;

	public List<SLPlace> select(int offset, int limit) throws SLDaoException;

	public int insert(SLPlace model) throws SLDaoException;

	public int insert(SLPlace model, SLTransaction transaction) throws SLDaoException;

	public int insert(List<SLPlace> models) throws SLDaoException;

	public int insertOrUpdate(SLPlace model) throws SLDaoException;

	public int insertOrUpdate(SLPlace model, SLTransaction transaction) throws SLDaoException;

	public int insert(List<SLPlace> models, SLTransaction transaction) throws SLDaoException;

	public int update(SLPlace model) throws SLDaoException;

	public int update(SLPlace model, SLTransaction transaction) throws SLDaoException;

	public int delete(String id) throws SLDaoException;

	public int delete(String id, SLTransaction transaction) throws SLDaoException;

}
