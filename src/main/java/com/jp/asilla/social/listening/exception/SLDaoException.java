package com.jp.asilla.social.listening.exception;


public class SLDaoException extends SLException {

	private static final long serialVersionUID = 788966847258774482L;

	public SLDaoException(Throwable e) {
		super(e);
	}

	public SLDaoException() {
		super();
	}

	public SLDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SLDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SLDaoException(String message) {
		super(message);
	}

}
