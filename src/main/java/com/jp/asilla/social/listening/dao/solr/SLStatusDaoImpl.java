package com.jp.asilla.social.listening.dao.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLStatusDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.dao.solr.entities.SLStatusEntity;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLStatusDaoImpl implements SLStatusDao, SLSolrConstants {

	@Override
	public SLStatus get(long id) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		SolrClient client = transaction.getSession();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%d", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
				SOLR_COLUMN_STATUS_ID, id));

		try {
			QueryResponse response = client.query(params);
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			if (statuses == null || statuses.size() == 0) {
				return null;
			} 
			return statuses.get(0);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public List<SLStatus> select(int offset, int limit) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		SolrClient client = transaction.getSession();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "itemType:status");
		params.set("start", offset);
		params.set("rows", limit);

		try {
			QueryResponse response = client.query(params);
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			return new ArrayList<SLStatus>(statuses);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insert(SLStatus model) throws SLDaoException {
		SLStatusEntity solrStatus = new SLStatusEntity(model);
		
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		SolrClient client = transaction.getSession();
		try {
			client.addBean(solrStatus);
			transaction.commit();
			return 1;
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insert(SLStatus model, SLTransaction transaction) throws SLDaoException {
		SLStatusEntity solrStatus = new SLStatusEntity(model);
		
		SolrClient client = transaction.getSession();
		try {
			client.addBean(solrStatus);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insertOrUpdate(SLStatus model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%d", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
					SOLR_COLUMN_STATUS_ID, model.getStatusId()));
			
			QueryResponse response = client.query(params);
			
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			
			SLStatusEntity solrStatus = new SLStatusEntity(model);
			if (statuses != null && statuses.size() > 0) {
				solrStatus.setId(statuses.get(0).getId());
			}
			client.addBean(solrStatus);
			transaction.commit();
			return 1;
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insertOrUpdate(SLStatus model, SLTransaction transaction) throws SLDaoException {

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%d", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
				SOLR_COLUMN_STATUS_ID, model.getStatusId()));
		
		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			
			SLStatusEntity solrStatus = new SLStatusEntity(model);
			if (statuses != null && statuses.size() > 0) {
				solrStatus.setId(statuses.get(0).getId());
			}
			client.addBean(solrStatus);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insert(List<SLStatus> models) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		SolrClient client = transaction.getSession();
		
		List<SLStatusEntity> solrStatuses = new ArrayList<SLStatusEntity>();
		
		for (SLStatus model : models) {
			SLStatusEntity solrStatus = new SLStatusEntity(model);
			solrStatus.setItemType(SOLR_COLUMN_TYPE_STATUS);
			solrStatuses.add(solrStatus);
		}
		
		try {
			client.addBeans(solrStatuses);
			transaction.commit();
			return 1;
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insert(List<SLStatus> models, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		
		List<SLStatusEntity> solrStatuses = new ArrayList<SLStatusEntity>();
		
		for (SLStatus model : models) {
			SLStatusEntity solrStatus = new SLStatusEntity(model);
			solrStatus.setItemType(SOLR_COLUMN_TYPE_STATUS);
			solrStatuses.add(solrStatus);
		}
		
		try {
			client.addBeans(solrStatuses);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int update(SLStatus model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%d", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
				SOLR_COLUMN_STATUS_ID, model.getStatusId()));
		
		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			
			if (statuses == null || statuses.size() == 0) {
				return 0;
			} else {
				SLStatusEntity solrStatus = new SLStatusEntity(model);
				solrStatus.setId(statuses.get(0).getId());
				
				client.addBean(solrStatus);
				transaction.commit();
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int update(SLStatus model, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%d", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
					SOLR_COLUMN_STATUS_ID, model.getStatusId()));
			
			QueryResponse response = client.query(params);
			
			List<SLStatusEntity> statuses = response.getBeans(SLStatusEntity.class);
			
			if (statuses == null || statuses.size() == 0) {
				return 0;
			} else {
				SLStatusEntity solrStatus = new SLStatusEntity(model);
				solrStatus.setId(statuses.get(0).getId());
				
				client.addBean(solrStatus);
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int delete(long id) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%d", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
				SOLR_COLUMN_STATUS_ID, id));
		
		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			List<SLStatusEntity> places = response.getBeans(SLStatusEntity.class);
			
			if (places == null || places.size() == 0) {
				return 0;
			} else {
				client.deleteById(places.get(0).getId());
				transaction.commit();
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int delete(long id, SLTransaction transaction) throws SLDaoException {

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%d", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_STATUS, 
				SOLR_COLUMN_STATUS_ID, id));
		
		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			List<SLStatusEntity> places = response.getBeans(SLStatusEntity.class);
			
			if (places == null || places.size() == 0) {
				return 0;
			} else {
				client.deleteById(places.get(0).getId());
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

}
