package com.psib.common.restclient;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.psib.common.JsonParser;


/**
 * @author DatHT
 * Jun 5, 2016
 * @email: datht0601@gmail.com
 */
public class MutiPartWriter {
	private Object value;
	private String name;

	public MutiPartWriter(MultiPart part, String name) {
		value = part.getValue();
		this.name = name;
	}
	
	public void writeToStream(final DataOutputStream outputStream) throws IOException {
		if (value == null) {
			this.writeHeader("text/plain; charset=UTF-8", outputStream);
		}
		else if (value instanceof Date) {
			this.writeHeader("text/plain; charset=UTF-8", outputStream);
			Date date = (Date) value;
			String dateStr = String.valueOf(date.getTime());
			byte[] bytes = dateStr.getBytes();
			outputStream.write(bytes, 0, bytes.length);
		} 
		else if (value instanceof byte[]) {
			this.writeHeader("application/octet-stream", outputStream);
			byte[] bytes = (byte[])value;
			outputStream.write(bytes, 0, bytes.length);
		}
		//Data is file
		else if(value instanceof File) {
			File file = (File) value;
			String dispo = getContentDisposition(file);
			this.writeHeader("application/octet-stream", dispo, outputStream);
			InputStream istream = new FileInputStream(file);
			ObjectWriter.writeInputStream(istream, outputStream);
		}
		// Data is input stream
		else if(value instanceof InputStream) {
			this.writeHeader("application/octet-stream", outputStream);
			ObjectWriter.writeInputStream((InputStream) value, outputStream);
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
			this.writeHeader("text/plain; charset=UTF-8", outputStream);
			byte[] utf8Bytes = value.toString().getBytes("UTF8");
			outputStream.write(utf8Bytes, 0, utf8Bytes.length);
		}
		// In case customize model object
		else {
			this.writeHeader("application/json; charset=UTF-8", outputStream);
			String jsonStr = JsonParser.toJson(value);
			byte[] utf8Bytes = jsonStr.getBytes("UTF8");
			outputStream.write(utf8Bytes, 0, utf8Bytes.length);
		}
	}
	
	protected void writeHeader(String contentType, DataOutputStream outputStream) throws IOException {
		outputStream.writeBytes("Content-Type: " + contentType + "\r\n");
		outputStream.writeBytes("Content-Disposition: " + "form-data; " + "name=\"" + name + "\"\r\n");
		outputStream.writeBytes("Content-Transfer-Encoding: binary\r\n");
		outputStream.writeBytes("\r\n");
	}
	
	protected void writeHeader(String contentType, String contentDisposition,
			DataOutputStream outputStream) throws IOException {
		outputStream.writeBytes("Content-Type: " + contentType + "\r\n");
		outputStream.writeBytes("Content-Disposition: " + contentDisposition + "\r\n");
		outputStream.writeBytes("Content-Transfer-Encoding: binary\r\n");
		//outputStream.writeBytes("Content-ID: <" + name + ">\r\n");
		outputStream.writeBytes("\r\n");
	}
	
	protected String getContentDisposition(File file) {
		String fileName = file.getName();
		String fileOrgName = getFileNameISO88591(fileName );
		return "form-data; " + "name=\"" + name + "\"; filename=\"" + fileOrgName + "\"";
	}
	
	protected String getFileNameISO88591(String fileName) {
		try {
			return new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(
					"Can not convert file as charset iso-8859-1", e);
		}
	}

	public static void writeBoundary(DataOutputStream outputStream) throws IOException {
		outputStream.writeBytes("\r\n--" + RestClient.BOUNDARY + "\r\n");
	}

	public static void writeFirstBoundary(DataOutputStream outputStream) throws IOException {
		outputStream.writeBytes("--" + RestClient.BOUNDARY + "\r\n");
	}

	public static void writeLastBoundary(DataOutputStream outputStream) throws IOException {
		outputStream.writeBytes("\r\n--" + RestClient.BOUNDARY + "--\r\n");
	}
}
