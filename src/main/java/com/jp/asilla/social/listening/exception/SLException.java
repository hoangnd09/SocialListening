package com.jp.asilla.social.listening.exception;


public class SLException extends Exception {

	private static final long serialVersionUID = -829677503863878309L;

	public SLException(Throwable e) {
		super(e);
	}

	public SLException() {
		super();
	}

	public SLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SLException(String message, Throwable cause) {
		super(message, cause);
	}

	public SLException(String message) {
		super(message);
	}

}
