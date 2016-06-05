package com.psib.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psib.common.JsonParser;

public class JsonFileCreator {

	protected static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	protected static final JsonParser PARSER = new JsonParser();

	public static <T> boolean createFile(T model, String name) {
		String json = PARSER.toJson(model);
		Writer writer = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("C://Workspace//entities//" + name + ".json");
			writer = new OutputStreamWriter(fos, "UTF-8");
			writer.write(json);
			System.out.println("Write success");
			return true;
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
}
