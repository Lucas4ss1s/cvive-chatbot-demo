package br.com.cvive.utils;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static ObjectMapper getObjectMapper(boolean quoteFieldNames) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.QUOTE_FIELD_NAMES, quoteFieldNames);
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return mapper;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T asObject(String json, Class klass) throws Exception {
		return json == null || json.trim().equals("") || klass == null ? null : (T) getObjectMapper(false).readValue(json.getBytes(StandardCharsets.UTF_8), klass);
	}

	public static String asString(Object entity, boolean quoteFieldNames) throws Exception {
		return entity == null ? null : JsonUtil.getObjectMapper(quoteFieldNames).writeValueAsString(entity);
	}
	
	public static String asString(Object entity) throws Exception {
		return entity == null ? null : JsonUtil.getObjectMapper(false).writeValueAsString(entity);
	}
}