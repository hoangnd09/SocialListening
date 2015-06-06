package com.jp.asilla.social.listening.exception;


public class SLConfigurationException extends SLException {

	private static final long serialVersionUID = -6947340013128573745L;

	public SLConfigurationException(Throwable e) {
		super(e);
	}

	public SLConfigurationException() {
		super();
	}

	public SLConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SLConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SLConfigurationException(String message) {
		super(message);
	}

}
