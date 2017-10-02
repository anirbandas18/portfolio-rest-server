package com.teenthofabud.portfolio.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.multipart.MultipartFile;

import com.teenthofabud.portfolio.core.exception.InvalidSearchMetadataException;
import com.teenthofabud.portfolio.core.exception.UnknownServerHttpRequestImplException;

public interface UtilityService {
	
	public Map<String,Object> pojo2Map(Object pojo);
	
	public Object map2POJO(Map<String,Object> map, Class<?> clazz) throws InvalidSearchMetadataException;
	
	public String matchesRequest(List<String> filteredPaths, HttpServletRequest request);
	
	public HttpServletRequest springToServletHttpRequestConverter(ServerHttpRequest spring)throws UnknownServerHttpRequestImplException;
	
	public String generateIdForModel(Object model);
	
	public File multiPartToFile(MultipartFile source) throws IOException;

}
