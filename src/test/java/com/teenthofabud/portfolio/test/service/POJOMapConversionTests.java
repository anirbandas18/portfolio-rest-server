package com.teenthofabud.portfolio.test.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.PathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.service.UtilityService;
import com.teenthofabud.portfolio.test.model.POJO;
import com.teenthofabud.portfolio.vo.ResponseVO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { PathMatcher.class, UtilityService.class, ObjectMapper.class })
public class POJOMapConversionTests {
	
	@Autowired
	private UtilityService util;
	
	private Map<String,Object> completeMap, partialMap, differentMap, emptyMap;
	private POJO completePojo;
	
	@Before
	public void init() {
		Long now = System.currentTimeMillis();
		
		completeMap = new LinkedHashMap<>();
		completeMap.put("id", 1);
		completeMap.put("salary", 76543245.78d);
		completeMap.put("name", "Emp1");
		completeMap.put("registration", now);
		
		partialMap = new LinkedHashMap<>();
		partialMap.put("id", 1);
		partialMap.put("salary", 76543245.78d);
		
		differentMap = new LinkedHashMap<>();
		differentMap.put("status", HttpStatus.OK);
		differentMap.put("message", "Found");
		
		emptyMap = new LinkedHashMap<>();
		
		completePojo = new POJO(1, 76543245.78d, "Emp1", now);
	}
	
	@Test
	public void shouldReturnCompletePOJOForCompleteMap() {
		Object o = util.map2POJO(completeMap, POJO.class);
		Assert.assertEquals(completePojo, o);
	}
	
	@Test
	public void shouldReturnCompleteMapForCompletePOJO() {
		Map<String,Object> map = util.pojo2Map(completePojo);
		Assert.assertEquals(map, completeMap);
	}
	
	@Test
	public void shouldNotMatchCompletePOJOForPartialMap() {
		Object o = util.map2POJO(partialMap, POJO.class);
		Assert.assertNotEquals(completePojo, o);
	}
	
	@Test
	public void shouldNotMatchCompletePOJOForDifferentMap() {
		Object o = util.map2POJO(differentMap, ResponseVO.class);
		Assert.assertNotEquals(completePojo, o);
	}

	@Test
	public void shouldNotMatchCompletePOJOForEmptyMap() {
		Object o = util.map2POJO(emptyMap, POJO.class);
		Assert.assertNotEquals(completePojo, o);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenMapAndPOJOAreDifferent() {
		util.map2POJO(differentMap, POJO.class);
	}
	
}
