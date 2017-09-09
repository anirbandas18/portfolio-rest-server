package com.teenthofabud.portfolio.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.exception.InvalidSearchMetadataException;
import com.teenthofabud.portfolio.service.UtilityService;

@Service
public class UtilityServiceImpl implements UtilityService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);
	
	@Autowired
	private ObjectMapper mapper;
	
	public Map<String,Object> pojo2Map(Object pojo) {
		Map<String,Object> parameters = mapper.convertValue(pojo, new TypeReference<Map<String, Object>>() {});
		return parameters;
	}

	public Object map2POJO(Map<String,Object> map, Class<?> clazz) throws InvalidSearchMetadataException {
		String schemaName = "";
		if(clazz.isAnnotationPresent(JsonRootName.class)) {
			JsonRootName ann = clazz.getDeclaredAnnotation(JsonRootName.class);
			schemaName = ann.value();
		} else {
			schemaName = clazz.getSimpleName();
		}
		try {
			Object pojo = mapper.convertValue(map, clazz);
			LOG.info("Successfully parsed search parameters to schema: {}", schemaName);
			return pojo;
		} catch (IllegalArgumentException e) {
			LOG.info("Failed to parse search parameters to schema: {}", schemaName);
			throw new InvalidSearchMetadataException(schemaName);
		}
	}
	
}
