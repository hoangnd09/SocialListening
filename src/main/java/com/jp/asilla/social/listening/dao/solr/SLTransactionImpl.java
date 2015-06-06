package com.jp.asilla.social.listening.dao.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import com.jp.asilla.social.listening.dao.SLTransaction;

public class SLTransactionImpl implements SLTransaction {

	private SolrClient client;
	
	public SLTransactionImpl(SolrClient client) {
		this.client = client;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSession() {
		return (T) client;
	}
	
	@Override
	public void close() {
		try {
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			// do nothing.
		}
	}

	@Override
	public void rollback() {
		try {
			if (client != null) {
				client.rollback();
			}
		} catch (IOException e) {
			// do nothing.
		} catch (SolrServerException e) {
			// do nothing.
		}
	}

	@Override
	public void commit() {
		try {
			if (client != null) {
				client.commit();
			}
		} catch (IOException e) {
			// do nothing.
		} catch (SolrServerException e) {
			// do nothing.
		}
	}

}
