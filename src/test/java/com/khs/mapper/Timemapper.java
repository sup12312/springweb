package com.khs.mapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class Timemapper {

	
	private final static Logger log = LoggerFactory.getLogger("Timemapper.class");
	
	@Autowired
	private Timemapper1 t;
	
	@Test
	public void test() {
		log.info(t.gettime());
	}

}
