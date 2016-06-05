package com.psib.common.restclient;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.psib.common.JsonParser;


/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class ObjectWriter {
	private String contentType;
	private IWritable writable;
	
	public String getContentType() {
		return contentType;
	}

	public ObjectWriter(final Object value) {
		if (value == null) {
			contentType = "text/plain; charset=UTF-8";
			writable = null;
		} else if (value instanceof Date) {
			contentType = "text/plain; charset=UTF-8";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					Date date = (Date) value;
					String dateStr = String.valueOf(date.getTime());
					byte[] bytes = dateStr.getBytes();
					outputStream.write(bytes, 0, bytes.length);
				}
			};
		} else if (value instanceof byte[]) {
			contentType = "application/octet-stream";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					byte[] bytes = (byte[]) value;
					outputStream.write(bytes, 0, bytes.length);
				}
			};
		}
		// Data is file
		else if (value instanceof File) {
			contentType = "application/octet-stream";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					InputStream istream = new FileInputStream((File) value);
					writeInputStream(istream, outputStream);
				}
			};
		}
		// Data is input stream
		else if (value instanceof InputStream) {
			contentType = "application/octet-stream";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					writeInputStream((InputStream) value, outputStream);
				}
			};
		}
		// Normal data type
		else if (value instanceof Long
			|| value instanceof Integer
			|| value instanceof Character
			|| value instanceof Byte
			|| value instanceof Double
			|| value instanceof String
			|| value instanceof Short 
			|| value instanceof Float
			|| value instanceof Boolean) {
			contentType = "text/plain; charset=UTF-8";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					byte[] utf8Bytes = value.toString().getBytes("UTF8");
					outputStream.write(utf8Bytes, 0, utf8Bytes.length);
				}
			};
		}
		// In case customize model object
		else {
			contentType = "application/json; charset=UTF-8";
			writable = new IWritable() {
				public void write(DataOutputStream outputStream) throws IOException {
					String jsonStr = JsonParser.toJson(value);
					byte[] utf8Bytes = jsonStr.getBytes("UTF8");
					outputStream.write(utf8Bytes, 0, utf8Bytes.length);
				}
			};
		}
	}

	private static interface IWritable {
		void write(DataOutputStream outputStream) throws IOException;
	}

	public void write(DataOutputStream outputStream) throws IOException {
		if(writable != null) {
			writable.write(outputStream);
		}
	}

	/**
	 * Write input stream to output stream
	 * 
	 * @param value
	 * @param outputStream
	 * @throws IOException
	 */
	public static void writeInputStream(InputStream value,
			DataOutputStream outputStream) throws IOException {
		int len;
		byte[] buffer = new byte[1024];
		try {
			while ((len = value.read(buffer, 0, 1024)) > 0) {
				outputStream.write(buffer, 0, len);
			}
		} finally {
			value.close();
		}
	}
}
