package com.psib.common.restclient;

/**
 * @author DatHT
 * Jun 5, 2016
 */
public class ReadException extends RuntimeException {
	private static final long serialVersionUID = 2744186756946554640L;
	
	public ReadException(String msg, Throwable e){
		super(msg, e);
	}
	
	public ReadException(String msg){
		super(msg);
	}
}
