package com.psib.common;

@SuppressWarnings("serial")
public class CronExpressionException extends Exception {

	public CronExpressionException(String message, Throwable e) {
		super(message, e);
	}

	public CronExpressionException(String messsage) {
		super(messsage);
	}
}
