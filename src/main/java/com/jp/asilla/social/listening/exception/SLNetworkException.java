package com.jp.asilla.social.listening.exception;


public class SLNetworkException extends SLException {

	private static final long serialVersionUID = -6947340013128573745L;

	public SLNetworkException(Throwable e) {
		super(e);
	}

	public SLNetworkException() {
		super();
	}

	public SLNetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SLNetworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public SLNetworkException(String message) {
		super(message);
	}

}
