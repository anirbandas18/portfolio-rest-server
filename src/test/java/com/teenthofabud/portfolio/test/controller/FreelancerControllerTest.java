package com.teenthofabud.portfolio.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.controller.FreelancerController;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.test.model.FreelancerTestData;
import com.teenthofabud.portfolio.vo.FreelancerVO;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(FreelancerController.class)
@ComponentScan(basePackages = {"com.teenthofabud.portfolio"})
public class FreelancerControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FreelancerService freelancerService;

	@Autowired
	private FreelancerTestData freelancerTestData;
	
	@Autowired
	private Freelancer freelancerModel;

	@Before
	public void init() {
		BeanWrapper source = PropertyAccessorFactory.forBeanPropertyAccess(freelancerTestData);
		BeanWrapper target = PropertyAccessorFactory.forBeanPropertyAccess(freelancerModel);
		PropertyDescriptor[] propertiesDescriptor = BeanUtils.getPropertyDescriptors(FreelancerTestData.class);
		List<PropertyDescriptor> properties = Arrays.asList(propertiesDescriptor);
		properties.forEach(p -> target.setPropertyValue(p.getName(), source.getPropertyValue(p.getName())));
	}

	@Test
	public void shouldReturnFreelancerStatusAndHttpOKWhenFreelancerDetailsIsPosted( ) throws Exception{
		FreelancerVO freelancerVO = new FreelancerVO();
		freelancerVO.setId(freelancerModel.getId());
		freelancerVO.setName(freelancerModel.getFirstName().concat(freelancerModel.getLastName()));
		freelancerVO.setChanged(Boolean.TRUE);
		String expectedResponse = objectMapper.writeValueAsString(freelancerVO);
		when(freelancerService.createFreelancer(freelancerModel))
			.thenReturn(freelancerModel.getId());
		mockMvc.perform(post("/portfolio/v1/freelancer", freelancerModel))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(expectedResponse));
	}
}
