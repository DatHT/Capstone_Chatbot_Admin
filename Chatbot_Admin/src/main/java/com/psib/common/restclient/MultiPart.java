package com.psib.common.restclient;


/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class MultiPart {
	private Object value;
	
	public MultiPart(Object value){
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
