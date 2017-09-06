package com.teenthofabud.portfolio.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UtilityService {
	
	@Autowired
	private ObjectMapper mapper;
	
	public Map<String,Object> pojo2Map(Object pojo) {
		Map<String,Object> parameters = mapper.convertValue(pojo, new TypeReference<Map<String, Object>>() {});
		return parameters;
	}

	public Object map2POJO(Map<String,Object> map, Class<?> clazz) {
		Object pojo = mapper.convertValue(map, clazz);
		return pojo;
	}
	
}
