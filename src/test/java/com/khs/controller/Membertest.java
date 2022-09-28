package com.khs.controller;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.khs.dto.Member;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class Membertest {

	
	private final static Logger log = LoggerFactory.getLogger("Membertest.class");
	@Autowired
	private WebApplicationContext wc;
	
	private MockMvc mo;	//요청과 응답
	
	@Before
	public void test1() {
		this.mo = MockMvcBuilders.webAppContextSetup(this.wc).build();
		log.info("test1확인");
	}
	
	//controller
	@Test
	public void test() {
		
		try {
			String rs = mo.perform(MockMvcRequestBuilders.post("/lo/login").
					param("id", "asd")
					.param("pw", "123123"))
					.andReturn()
					.getModelAndView()
					.getViewName();
			log.info(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
