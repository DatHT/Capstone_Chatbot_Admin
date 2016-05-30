package com.psib.common;

import java.util.List;
import java.util.Objects;

/**
 * Created by DatHT on 11/28/2015.
 */
public interface IJsonParser {

    public <T> List<T> toList(String jsonString, Class<T> cls) throws JsonParserException;

    public <T> T toObject(String jsonString, Class<T> cls) throws JsonParserException;

    String toJson(Object value) throws JsonParserException;
}
