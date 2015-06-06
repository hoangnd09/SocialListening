package com.jp.asilla.social.listening.dao;

public interface SLTransaction {
	
	public <T> T getSession();
	
	public void close();
	
	public void rollback();
	
	public void commit();

	
}
