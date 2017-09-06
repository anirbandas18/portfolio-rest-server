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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.PathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.service.UtilityService;
import com.teenthofabud.portfolio.test.model.POJO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { PathMatcher.class, UtilityService.class, ObjectMapper.class })
public class UtilityServiceTest {
	
	@Autowired
	private UtilityService util;
	
	private Map<String,Object> map;
	private POJO pojo;
	
	@Before
	public void init() {
		Long now = System.currentTimeMillis();
		
		map = new LinkedHashMap<>();
		map.put("id", 1);
		map.put("salary", 76543245.78d);
		map.put("name", "Emp1");
		map.put("registration", now);
		
		pojo = new POJO(1, 76543245.78d, "Emp1", now);
	}
	
	@Test
	public void shouldReturnPOJOWhenMapIsGiven() {
		Object o = util.map2POJO(map, POJO.class);
		Assert.assertEquals(pojo, o);
	}
	
	@Test
	public void shouldReturnMapWhenPOJOIsGiven() {
		Map<String,Object> map = util.pojo2Map(pojo);
		Assert.assertEquals(map, this.map);
	}

}
