package com.psib.common.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.psib.common.JsonParser;


/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class RestResult {
	private InputStream inputStream;
	private String resultStr;
	private HttpURLConnection conn;
	private int statusCode;

	public RestResult(HttpURLConnection conn) throws IOException {
		this.conn = conn;
		try {
			this.statusCode = conn.getResponseCode();
		} catch (IOException e) {
			this.statusCode = conn.getResponseCode();
		}	
		this.statusCode = conn.getResponseCode();
		if (statusCode != HttpURLConnection.HTTP_OK) {
			inputStream = conn.getErrorStream();
		} else {
			inputStream = conn.getInputStream();
		}
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public boolean isOk() {
		return statusCode == HttpURLConnection.HTTP_OK;
	}
	
	public Map<String, List<String>> getHeaders() {
		return conn.getHeaderFields();
	}
	
	public String getHeader(String name) {
		return conn.getHeaderField(name);
	}
	
	public String getContentType() {
		return conn.getContentType();
	}
	
	public long getContentLength() {
		return conn.getContentLengthLong();
	}
	
	public boolean getAllowUserInteraction() {
		return conn.getAllowUserInteraction();
	}
	
	public String getContentEncoding() {
		return getContentEncoding(null);
	}
	
	public String getContentEncoding(String defCharset) {
		String encoding = conn.getContentEncoding();
		if(encoding == null) {
			String contentType = conn.getContentType();
			if(contentType != null) {
				String[] values = contentType.split(";");
				for (String value : values) {
				    value = value.trim();
				    if (value.toLowerCase().startsWith("charset=")) {
				        return value.substring("charset=".length());
				    }
				}
			}
		} else {
			return encoding;
		}
		return defCharset;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public <T> T parse(Class<T> modelClass) {
		return JsonParser.toObject(toString(), modelClass);
	}
	
	public <T> T parse(Class<T> baseClass, Class<?>... paramsCls) {
		return JsonParser.toObject(toString(), baseClass, paramsCls);
	}


		public <T> List<T> toList(Class<T> clazz) {
		return JsonParser.toList(toString(), clazz);
	}

	public int toInt() {
		return Integer.parseInt(toString());
	}

	public long toLong() {
		return Long.parseLong(toString());
	}

	public double toDouble() {
		return Double.parseDouble(toString());
	}

	public float toFloat() {
		return Float.parseFloat(toString());
	}

	public char toCharacter() {
		String val = toString();
		return val.charAt(0);
	}

	public boolean toBoolean() {
		return Boolean.parseBoolean(toString());
	}

	public short toShort() {
		return Short.parseShort(toString());
	}

	public byte toByte() {
		return Byte.parseByte(toString());
	}
	
	@Override
	public String toString() {
		String charset = getContentEncoding("UTF-8");
		return this.toString(charset);
	}

	public String toString(String charset) {
		if (this.resultStr == null) {
			try {
				this.resultStr = IOUtils.toString(inputStream);
			} catch (Exception e) {
				throw new ReadException("Unnable to read string content of response stream!", e);
			} finally {
				this.close();
			}
		}
		return resultStr;
	}
	
	public void close() {
		if(inputStream != null) {
			try {
				this.inputStream.close();
			} catch (IOException e) {
				//Do nothing
			}
			conn.disconnect();
		}
	}
}
