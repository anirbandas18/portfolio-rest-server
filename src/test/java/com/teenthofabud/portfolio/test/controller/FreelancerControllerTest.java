/*package com.teenthofabud.portfolio.test.controller;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.controller.FreelancerController;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.repository.FreelancerRepository;
import com.teenthofabud.portfolio.service.FreelancerService;
import com.teenthofabud.portfolio.test.model.FreelancerTestData;

@RunWith(SpringRunner.class)
@WebMvcTest(FreelancerController.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FreelancerControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FreelancerService freelancerService;
	
	@MockBean
	private FreelancerRepository repository;

	@Autowired
	private FreelancerTestData freelancerTestData;

	@Autowired
	private Freelancer freelancerModel;

	@Before
	public void init() {
		//mockMvc = standaloneSetup(new FreelancerController()).build();
		BeanWrapper source = PropertyAccessorFactory.forBeanPropertyAccess(freelancerTestData);
		BeanWrapper target = PropertyAccessorFactory.forBeanPropertyAccess(freelancerModel);
		PropertyDescriptor[] propertiesDescriptor = BeanUtils.getPropertyDescriptors(FreelancerTestData.class);
		List<PropertyDescriptor> properties = Arrays.asList(propertiesDescriptor);
		System.out.println(properties);
		properties.forEach(p -> target.setPropertyValue(p.getName(), source.getPropertyValue(p.getName())));
	}

	@Test
	public void shouldReturnFreelancerStatusAndHttpOKWhenFreelancerDetailsIsPosted() throws Exception {
		FreelancerVO freelancerVO = new FreelancerVO();
		freelancerVO.setId(freelancerModel.getId());
		freelancerVO.setName(freelancerModel.getFirstName().concat(freelancerModel.getLastName()));
		freelancerVO.setChanged(Boolean.TRUE);
		String expectedResponse = objectMapper.writeValueAsString(freelancerVO);
		given(freelancerService.createFreelancer(freelancerModel)).willReturn(freelancerModel.getId());
		mockMvc.perform(post("/portfolio/v1/freelancer", freelancerModel)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
	}
	
	@Test
	public void shouldReturnJSONContentTypeAndHttpOKWhenFreelancerDetailsIsPosted() throws Exception {
		mockMvc.perform(post("/portfolio/v1/freelancer", freelancerModel))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
*/