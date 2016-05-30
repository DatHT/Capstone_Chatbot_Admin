package com.psib.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

/**
 * Created by DatHT on 11/28/2015.
 */
public class JsonParser implements IJsonParser {
    private ObjectMapper mapper;

    public JsonParser(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public <T> List<T> toList(String jsonString, Class<T> cls) throws JsonParserException {
        CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, cls);
        try {
            return (List<T>) mapper.readValue(jsonString, listType);
        } catch (Exception e) {
            throw new JsonParserException("Unable to parse json string to list", e);
        }
    }

    @Override
    public <T> T toObject(String jsonString, Class<T> cls) throws JsonParserException {
        try {
            return mapper.readValue(jsonString, cls);
        } catch (Exception e) {
            throw new JsonParserException("Unnable to parse json string to object!", e);
        }
    }

    @Override
    public String toJson(Object value) throws JsonParserException {
        try {
            return mapper.writeValueAsString(value);
        }catch (Exception e) {
            throw new JsonParserException("Can not parse object to json", e);
        }
    }
}
