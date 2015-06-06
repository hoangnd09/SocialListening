package com.jp.asilla.social.listening.dao.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.jp.asilla.social.listening.SLSetting;
import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLJobDao;
import com.jp.asilla.social.listening.dao.SLPlaceDao;
import com.jp.asilla.social.listening.dao.SLStatusDao;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.exception.SLConfigurationException;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLDaoFactoryImpl extends SLDaoFactory {
	private static final String CONFIGURE_KEY = "solr.baseUrl";
	
	@Override
	public SLTransaction openTransaction() throws SLDaoException {
		try {
			SolrClient client = new HttpSolrClient(SLSetting.getSetting(CONFIGURE_KEY));
			return new SLTransactionImpl(client);
		} catch (SLConfigurationException e) {
			throw new SLDaoException(e);
		}
	}
	
	@Override
	public SLJobDao getJobDao() {
		return new SLJobDaoImpl();
	}
	
	@Override
	public SLStatusDao getStatusDao() {
		return new SLStatusDaoImpl();
	}
	
	@Override
	public SLPlaceDao getPlaceDao() {
		return new SLPlaceDaoImpl();
	}
}
