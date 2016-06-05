package com.psib.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by DatHT on 11/28/2015.
 * @email: datht0601@gmail.com
 */
public class JsonParser {

	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String jsonString, Class<T> cls) {
		ObjectMapper mapper = new ObjectMapper();
		CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, cls);
		try {
			return (List<T>) mapper.readValue(jsonString, listType);
		} catch (Exception e) {
			throw new JsonParserException("Unnable to parse json string to list/array!", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(String jsonString, Class<T> baseCls, Class<?>... paramCls) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = TypeFactory.defaultInstance().constructParametricType(baseCls, paramCls);
		try {
			return (T) mapper.readValue(jsonString, type);
		} catch (Exception e) {
			throw new JsonParserException("Unnable to parse json string to parametric object!", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(String jsonString, Class<T> cls) {
		List<T> list = toList(jsonString, cls);
		T[] result = (T[]) Array.newInstance(cls, list.size());
		list.toArray(result);
		return result;
	}

	public static <T> T toObject(String jsonString, Class<T> cls) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, cls);
		} catch (Exception e) {
			throw new JsonParserException("Unnable to parse json string to object!", e);
		}
	}

	public static String toJson(Object value) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			throw new JsonParserException("Can not parse object to Json!", e);
		}
	}}
