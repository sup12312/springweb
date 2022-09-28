package com.khs.controller;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml","file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(MemberControllerTest.class);
	
	@Inject
	private WebApplicationContext wac;
	private MockMvc mockMvc; //브라우저에서 요청과 응답을 의미하는 객체
	@Before
	public void setup() {
	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		log.info("setup....");
	}
	
	
	  @Test public void testLogin() { 
		  try { 
			  String rs =
			  mockMvc.perform(MockMvcRequestBuilders.post("/lo/login") .param("id", "asd")
			  .param("pw", "123123") ).andReturn().getModelAndView().getViewName();
			  log.info(rs);
		  } catch (Exception e) { 
			  e.printStackTrace(); 
		  } 
	  }
	 
	
}
