package com.jp.asilla.social.listening.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.entities.SLSocialNetwork;
import com.jp.asilla.social.listening.dao.solr.SLDaoFactoryImpl;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SolrApplication {
	public static void main(String[] args) throws SLDaoException {
		
		deleteAll();
		addJob();
	}
	
	private static void testGetJob() throws SLDaoException {
		ModifiableSolrParams parameters = new ModifiableSolrParams();
		parameters.set("q", "itemType:job");
		
		SLTransaction transaction = new SLDaoFactoryImpl().openTransaction();
		
		SolrClient client = transaction.getSession();
		try {
			QueryResponse response = client.query(parameters);
			
			System.out.println(response.toString());
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		}
	}
	
	private static void addJob() throws SLDaoException {
		SLJob job = new SLJob();
		job.setName("Twitter collector, in Hanoi");
		job.setNetwork(SLSocialNetwork.TWITTER.getValue());
		job.setExpression("0 * * * * ?");
		job.setTimezone("GMT+9");
		job.setLatitude(21.0333);
		job.setLongitude(105.85);
		job.setRadius(300);
		job.setState("{\"since_date\":\"May 17, 2015 12:00:16 AM\"}");
		
		SLDaoFactory.getInstance().getJobDao().insert(job);
	}
	
	private static void deleteAll() throws SLDaoException {
		try {
			SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
			SolrClient client = transaction.getSession();
			client.deleteByQuery("*");
			client.commit();
			client.close();
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		}
	}
	
	private static void addRecord() throws SLDaoException {
		try {
			SolrClient client = SLDaoFactory.getInstance().openTransaction().getSession();
			for (int i = 0; i < 1000; ++i) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", "book-" + i);
				client.add(doc, 100);
			}
			client.deleteByQuery("*");
			client.commit();
			client.close();
			
		} catch (SolrServerException | IOException e) {
			throw new SLDaoException(e);
		}
	}
	
}
