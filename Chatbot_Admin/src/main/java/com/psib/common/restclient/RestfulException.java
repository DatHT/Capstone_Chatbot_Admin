package com.psib.common.restclient;

import java.io.IOException;

public class RestfulException extends Exception {
	private static final long serialVersionUID = -697924829353679221L;
	private int statusCode;

	public RestfulException(int statusCode) {
		this.statusCode = statusCode;
	}

	public RestfulException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public RestfulException(String message, int statusCode, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public RestfulException(int statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

	public RestfulException(RestResult result) throws IOException {
		super(result.toString());
		this.statusCode = result.getStatusCode();
	}

	public int getStatusCode() {
		return statusCode;
	}
}
