package com.teenthofabud.portfolio.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.exception.InvalidSearchMetadataException;
import com.teenthofabud.portfolio.core.exception.InvalidSearchParametersException;
import com.teenthofabud.portfolio.core.exception.UnknownServerHttpRequestImplException;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.service.UtilityService;

@Service
public class UtilityServiceImpl implements UtilityService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);
	
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private Validator validator;
	
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
	
	@Override
	public HttpServletRequest springToServletHttpRequestConverter(ServerHttpRequest spring) throws UnknownServerHttpRequestImplException{
		HttpServletRequest servletRequest = null;
		if(spring instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest springRequest = (ServletServerHttpRequest) spring;
			servletRequest =  springRequest.getServletRequest();
		} else if (spring instanceof RequestPartServletServerHttpRequest) {
			RequestPartServletServerHttpRequest springRequest = (RequestPartServletServerHttpRequest) spring;
			servletRequest = springRequest.getServletRequest();
		} else {
			throw new UnknownServerHttpRequestImplException(spring.getClass());
		}
		return servletRequest;
	}

	@Override
	public String matchesRequest(List<String> filteredPaths, HttpServletRequest request) {
		List<AntPathRequestMatcher> matchers = filteredPaths.stream().map(pattern -> new AntPathRequestMatcher(pattern))
				.collect(Collectors.toList());
		Collections.sort(matchers, pathPatternComparator);
		int start = 0, end = matchers.size() - 1;
		String servletPath = request.getServletPath();
		while (start <= end) {
			int mid = (start + end) / 2;
			AntPathRequestMatcher element = matchers.get(mid);
			int comparison = element.getPattern().compareTo(servletPath);
			if (element.matches(request)) {
				servletPath = "";
				break;
			} else if (comparison > 0) {
				end = mid - 1;
			} else if (comparison < 0) {
				start = mid + 1;
			}
		}
		return servletPath;
	}
	
	private Comparator<AntPathRequestMatcher> pathPatternComparator = new Comparator<AntPathRequestMatcher>() {

		@Override
		public int compare(AntPathRequestMatcher o1, AntPathRequestMatcher o2) {
			// TODO Auto-generated method stub
			return o1.getPattern().compareTo(o2.getPattern());
		}

	};

	@Override
	public void validateBean(Object pojo) throws InvalidSearchParametersException {
		// TODO Auto-generated method stub
		LOG.info("Manual triggering of validation for request parameters");
		Set<ConstraintViolation<Detail>> errors = validator.validate(pojo);
	}
	
}
