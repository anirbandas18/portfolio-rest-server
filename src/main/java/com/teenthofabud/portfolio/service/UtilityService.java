package com.teenthofabud.portfolio.service;

import java.util.Map;

import com.teenthofabud.portfolio.core.exception.InvalidSearchParametersException;

public interface UtilityService {
	
	public Map<String,Object> pojo2Map(Object pojo);
	
	public Object map2POJO(Map<String,Object> map, Class<?> clazz) throws InvalidSearchParametersException;

}
