package com.teenthofabud.portfolio.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.exception.InvalidSearchParametersException;

@Service
public class UtilityServiceImpl {
	
	@Autowired
	private ObjectMapper mapper;
	
	public Map<String,Object> pojo2Map(Object pojo) {
		Map<String,Object> parameters = mapper.convertValue(pojo, new TypeReference<Map<String, Object>>() {});
		return parameters;
	}

	public Object map2POJO(Map<String,Object> map, Class<?> clazz) throws InvalidSearchParametersException {
		try {
			Object pojo = mapper.convertValue(map, clazz);
			return pojo;
		} catch (IllegalArgumentException e) {
			String schemaName = "";
			if(clazz.isAnnotationPresent(JsonRootName.class)) {
				JsonRootName ann = clazz.getDeclaredAnnotation(JsonRootName.class);
				schemaName = ann.value();
			} else {
				schemaName = clazz.getSimpleName();
			}
			throw new InvalidSearchParametersException(schemaName);
		}
	}
	
}
