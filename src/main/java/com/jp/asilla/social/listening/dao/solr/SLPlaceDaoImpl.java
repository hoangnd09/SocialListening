package com.jp.asilla.social.listening.dao.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLPlaceDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.dao.solr.entities.SLPlaceEntity;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLPlaceDaoImpl implements SLPlaceDao, SLSolrConstants {

	@Override
	public SLPlace get(String id) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
				SOLR_COLUMN_PLACE_ID, id));

		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			if (places == null || places.size() == 0) {
				return null;
			} 
			return places.get(0);
			
		} catch (SolrServerException | IOException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public List<SLPlace> select(int offset, int limit) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s", SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE));
		params.set("start", offset);
		params.set("rows", limit);

		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			return new ArrayList<SLPlace>(places);
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insert(SLPlace model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		SLPlaceEntity solrPlace = new SLPlaceEntity(model);
		
		try {
			SolrClient client = transaction.getSession();
			client.addBean(solrPlace);
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
	public int insert(SLPlace model, SLTransaction transaction) throws SLDaoException {
		SLPlaceEntity solrPlace = new SLPlaceEntity(model);
		
		try {
			SolrClient client = transaction.getSession();
			client.addBean(solrPlace);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insert(List<SLPlace> models) throws SLDaoException {
		List<SLPlaceEntity> solrPlaces = new ArrayList<SLPlaceEntity>();
		for (SLPlace model : models) {
			SLPlaceEntity solrPlace = new SLPlaceEntity(model);
			solrPlaces.add(solrPlace);
		}
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		try {
			SolrClient client = transaction.getSession();
			client.addBeans(solrPlaces);
			transaction.commit();
			return solrPlaces.size();
		} catch (IOException | SolrServerException e) {
			transaction.rollback();
			throw new SLDaoException(e);
		} finally {
			transaction.close();
		}
	}

	@Override
	public int insertOrUpdate(SLPlace model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
				SOLR_COLUMN_PLACE_ID, model.getPlaceId()));
		
		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
			SLPlaceEntity solrPlace = new SLPlaceEntity(model);
			if (places != null && places.size() > 0) {
				solrPlace.setId(places.get(0).getId());
			}
			client.addBean(solrPlace);
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
	public int insertOrUpdate(SLPlace model, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
					SOLR_COLUMN_PLACE_ID, model.getPlaceId()));
			
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
			SLPlaceEntity solrPlace = new SLPlaceEntity(model);
			if (places != null && places.size() > 0) {
				solrPlace.setId(places.get(0).getId());
			}
			client.addBean(solrPlace);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int insert(List<SLPlace> models, SLTransaction transaction) throws SLDaoException {
		
		List<SLPlaceEntity> solrPlaces = new ArrayList<SLPlaceEntity>();
		
		for (SLPlace model : models) {
			SLPlaceEntity solrPlace = new SLPlaceEntity(model);
			solrPlaces.add(solrPlace);
		}
		
		SolrClient client = transaction.getSession();
		try {
			client.addBeans(solrPlaces);
			return 1;
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int update(SLPlace model) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
				SOLR_COLUMN_PLACE_ID, model.getPlaceId()));
		
		try {
			SolrClient client = transaction.getSession();
			
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
			if (places == null || places.size() == 0) {
				return 0;
			} else {
				SLPlaceEntity solrPlace = new SLPlaceEntity(model);
				solrPlace.setId(places.get(0).getId());
				
				client.addBean(solrPlace);
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
	public int update(SLPlace model, SLTransaction transaction) throws SLDaoException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
				SOLR_COLUMN_PLACE_ID, model.getPlaceId()));
		
		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
			if (places == null || places.size() == 0) {
				return 0;
			} else {
				SLPlaceEntity solrPlace = new SLPlaceEntity(model);
				solrPlace.setId(places.get(0).getId());
				
				client.addBean(solrPlace);
				return 1;
			}
		} catch (IOException | SolrServerException e) {
			throw new SLDaoException(e);
		}
	}

	@Override
	public int delete(String id) throws SLDaoException {
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", String.format("%s:%s AND %s:%s", 
				SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
				SOLR_COLUMN_PLACE_ID, id));
		
		try {
			SolrClient client = transaction.getSession();
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
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
	public int delete(String id, SLTransaction transaction) throws SLDaoException {
		SolrClient client = transaction.getSession();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", String.format("%s:%s AND %s:%s", 
					SOLR_COLUMN_TYPE, SOLR_COLUMN_TYPE_PLACE, 
					SOLR_COLUMN_PLACE_ID, id));
			
			QueryResponse response = client.query(params);
			
			List<SLPlaceEntity> places = response.getBeans(SLPlaceEntity.class);
			
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
