package com.psib.common.restclient;

/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class EncodeException extends RuntimeException {
	private static final long serialVersionUID = 2830144019530252595L;

	public EncodeException(String message) {
		super(message);
	}

	public EncodeException(String message, Throwable e) {
		super(message, e);
	}
}
