package com.psib.common.restclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class RestClient {
	public static final List<RequestMethod> ignoreMethods = Arrays
			.asList(new RequestMethod[] { RequestMethod.GET,
					 RequestMethod.HEAD,
					RequestMethod.OPTIONS });

	public static final int READ_TIMEOUT = 2 * 60 * 1000; // 2minute
	public static final int CONNECT_TIMEOUT = READ_TIMEOUT;

	public static final String BOUNDARY = "----JVptU72dTu3";
	
	public static final String CONTENT_TYPE = "Content-Type";

	private String urlStr;

	public RestClient(String url) {
		this.urlStr = url;
	}

	public static String encodeURI(String value, String charset) {
		try {
			String result = URLEncoder.encode(value, charset);
			if ("UTF-8".equalsIgnoreCase(charset)) {
				return result.replace("+", "%20");
			}
			return result;
		} catch (Exception e) {
			throw new EncodeException("Encode request failed!", e);
		}
	}

	public static String convertToString(Object value) {
		if (value instanceof Date) {
			Date date = (Date) value;
			return String.valueOf(date.getTime());
		}
		return value.toString();
	}

	public static String toQueryParameter(Object value, String encoding) {
		String valStr = convertToString(value);
		return encodeURI(valStr, encoding);
	}

	public class RestRequest {
		private ArrayList<MutiPartWriter> partParams;
		private ArrayList<String> routes;
		private Hashtable<String, String> params;
		private Hashtable<String, String> headers;
		private String methodName;
		private String methodType;
		private boolean isInOut;
		private boolean hasContentType;

		private RestRequest(String methodName, String methodType,
				boolean isInOut) throws IOException {
			this.methodName = methodName;
			this.methodType = methodType;
			this.isInOut = isInOut;
			params = new Hashtable<String, String>();
			headers = new Hashtable<String, String>();
			partParams = new ArrayList<MutiPartWriter>();
			routes = new ArrayList<String>();
		}

		/**
		 * Build query string parameters
		 */
		private String buildQueryParams() {
			Enumeration<String> keys = params.keys();
			StringBuilder queryParms = new StringBuilder("?");
			boolean isBegin = true;
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = params.get(key);
				if (value != null) {
					if (isBegin) {
						isBegin = false;
					} else {
						queryParms.append("&");
					}
					queryParms.append(key).append("=").append(value);
				}
			}
			if (queryParms.length() > 1) {
				return queryParms.toString();
			}
			return "";
		}

		private String buildRoutePath() {
			StringBuilder builder = new StringBuilder();
			for (String value : routes) {
				builder.append('/').append(value);
			}
			return builder.toString();
		}

		private HttpURLConnection createHttpUrlConn() throws IOException {
			String queryParams = this.buildQueryParams();
			String routePath = this.buildRoutePath();
			StringBuilder urlBuilder = new StringBuilder(urlStr);
			if (methodName != null) {
				if (!urlStr.endsWith("/")) {
					urlBuilder.append('/');
				}
				urlBuilder.append(methodName);
			}
			urlBuilder.append(routePath).append(queryParams);
			URL url = new URL(urlBuilder.toString());

			// Create http url connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setReadTimeout(READ_TIMEOUT);
			connection.setConnectTimeout(CONNECT_TIMEOUT);

			// Support get response stream and allow write to request stream
			if (isInOut) {
				connection.setDoInput(true);
				connection.setDoOutput(true);
			}

			connection.setUseCaches(false);

			connection.setRequestMethod(methodType);

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Pragma", "no-cache");

			if (!hasContentType()) {
				if (partParams.size() > 0) {
					connection.setRequestProperty(CONTENT_TYPE,
							"multipart/form-data" + "; boundary=" + BOUNDARY);
				} else {
					connection.setRequestProperty(CONTENT_TYPE,
							"application/json" + "; charset=UTF-8");
				}
			}

			// Default is */*
			if (!headers.containsKey("Accept")) {
				connection.setRequestProperty("Accept", "*/*");
			}

			// Add custom header
			Enumeration<String> keys = headers.keys();
			while (keys.hasMoreElements()) {
				// get key
				String key = keys.nextElement();
				// get value
				String value = headers.get(key);
				connection.setRequestProperty(key, value);
			}

			connection.connect();
			return connection;
		}

		public RestRequest setContentType(String contentType) {
			this.addHeader(CONTENT_TYPE, contentType);
			this.hasContentType = true;
			return this;
		}
		
		public RestRequest setNoContentType() {
			this.hasContentType = true;
			return this;
		}
		
		private boolean hasContentType() {
			return this.hasContentType;
		}

		public RestRequest setAccept(String accept) {
			this.addHeader("Accept", accept);
			return this;
		}

		public RestRequest addHeader(String key, String value) {
			headers.put(key, value);
			return this;
		}

		public RestRequest addParameter(String name, Object value) {
			if (value instanceof MultiPart) {
				MutiPartWriter item = new MutiPartWriter((MultiPart) value, name);
				partParams.add(item);
			} else if (value != null) {
				String valStr = convertToString(value);
				params.put(name, valStr);
			}
			return this;
		}

		public RestRequest addParameter(String name, Object value,
				String encoding) {
			if (value instanceof MultiPart) {
				throw new IllegalArgumentException(
						"The method only used for request parameter!");
			} else if (value != null) {
				String valStr = toQueryParameter(value, encoding);
				params.put(name, valStr);
			}
			return this;
		}

		public RestRequest addRoute(Object value) {
			String valStr = convertToString(value);
			routes.add(valStr);
			return this;
		}
		

		public RestRequest addRoute(Object value, String encoding) {
			String valStr = toQueryParameter(value, encoding);
			routes.add(valStr);
			return this;
		}

		public RestResult invoke() throws IOException {
			HttpURLConnection conn = createHttpUrlConn();
			DataOutputStream outputStream = null;
			try {
				if (partParams.size() > 0) {
					outputStream = new DataOutputStream(conn.getOutputStream());
					boolean writed = false;
					for (MutiPartWriter multiPart : partParams) {
						if (writed) {
							MutiPartWriter.writeBoundary(outputStream);
						} else {
							MutiPartWriter.writeFirstBoundary(outputStream);
							writed = true;
						}
						multiPart.writeToStream(outputStream);
					}
					MutiPartWriter.writeLastBoundary(outputStream);
				}
			} finally {
				if (null != outputStream) {
					outputStream.close();
				}
			}

			return new RestResult(conn);
		}

		/**
		 * Invoke to server
		 * 
		 * @param bodyObject
		 * @return RestResult response
		 * @throws IOException
		 */
		public RestResult invoke(Object bodyObject) throws IOException {
			ObjectWriter writer = new ObjectWriter(bodyObject);
			if(!hasContentType()) {
				setContentType(writer.getContentType());
			}
			HttpURLConnection conn = createHttpUrlConn();
			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			try {
				writer.write(outputStream);
			} finally {
				outputStream.close();
			}
			return new RestResult(conn);
		}
	}

	public RestRequest createInvoker(RequestMethod method) throws IOException {
		boolean isInOut = !ignoreMethods.contains(method);
		return new RestRequest(null, method.name(), isInOut);
	}

	public RestRequest createInvoker(String methodName, RequestMethod method)
			throws IOException {
		boolean isInOut = !ignoreMethods.contains(method);
		return new RestRequest(methodName, method.name(), isInOut);
	}
}
