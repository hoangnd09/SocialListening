package com.jp.asilla.social.listening.dao.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLJobDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.solr.entities.SLJobEntity;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLJobDaoImpl implements SLJobDao, SLSolrConstants {

	@Override
	public SLJob get(String jobID) throws SLDaoException {
		SLTransaction transaction = new SLDaoFactoryImpl().openTransaction();

		ModifiableSolrParams parameters = new ModifiableSolrParams();
		parameters.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
				SOLR_COLUMN_JOB_ID, jobID));

		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(parameters);
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			if (jobs == null || jobs.size() == 0) {
				return null;
			} 
			return jobs.get(0);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public List<SLJob> select() throws SLDaoException {
		SLTransaction transaction = new SLDaoFactoryImpl().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "itemType:job");

		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			List<SLJobEntity> jobEntities = response.getBeans(SLJobEntity.class);
			return new ArrayList<SLJob>(jobEntities);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public List<SLJob> select(int offset, int limit) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "itemType:job");
		params.set("start", offset);
		params.set("rows", limit);

		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			List<SLJobEntity> jobEntities = response.getBeans(SLJobEntity.class);
			return new ArrayList<SLJob>(jobEntities);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insert(SLJob model) throws SLDaoException {
		SLJobEntity solrJob = new SLJobEntity(model);
		
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		try {
			SolrClient client = transaction.getSession();
			client.addBean(solrJob);
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
	public int insert(SLJob model, SLTransaction transaction) throws SLDaoException {
		SLJobEntity solrJob = new SLJobEntity(model);
		
		SolrClient client = transaction.getSession();
		try {
			client.addBean(solrJob);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int update(SLJob model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
					SOLR_COLUMN_JOB_ID, model.getJobId()));
		
		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			if (jobs == null || jobs.size() == 0) {
				return 0;
			} else {
				SLJobEntity solrJob = new SLJobEntity(model);
				solrJob.setId(jobs.get(0).getId());
				
				client.addBean(solrJob);
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
	public int update(SLJob model, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
					SOLR_COLUMN_JOB_ID, model.getJobId()));
			
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			if (jobs == null || jobs.size() == 0) {
				return 0;
			} else {
				SLJobEntity solrJob = new SLJobEntity(model);
				solrJob.setId(jobs.get(0).getId());
				
				client.addBean(solrJob);
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int delete(String jobID) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
				SOLR_COLUMN_JOB_ID, jobID));
		
		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			if (jobs == null || jobs.size() == 0) {
				return 0;
			} else {
				client.deleteById(jobs.get(0).getId());
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
	public int delete(String jobID, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
					SOLR_COLUMN_JOB_ID, jobID));
			
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			if (jobs == null || jobs.size() == 0) {
				return 0;
			} else {
				client.deleteById(jobs.get(0).getId());
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insertOrUpdate(SLJob model, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
					SOLR_COLUMN_JOB_ID, model.getJobId()));
			
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			SLJobEntity solrJob = new SLJobEntity(model);
			if (jobs != null && jobs.size() > 0) {
				solrJob.setId(jobs.get(0).getId());
			}
			client.addBean(solrJob);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int updateState(String jobID, String state, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_JOB, 
					SOLR_COLUMN_JOB_ID, jobID));
			
			QueryResponse response = client.query(params);
			
			List<SLJobEntity> jobs = response.getBeans(SLJobEntity.class);
			
			if (jobs == null || jobs.size() == 0) {
				return 0;
			} else {
				SLJobEntity solrJob = jobs.get(0);
				solrJob.setState(state);
				client.addBean(solrJob);
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

}
