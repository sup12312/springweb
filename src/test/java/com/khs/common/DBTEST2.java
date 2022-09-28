package com.khs.common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DBTEST2 {

	
	private final static Logger log = LoggerFactory.getLogger("DBTEST2.class");
	
	@Test
	public void test() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.10.161:1521/xe","asd","aaaa");
			log.info(conn.toString());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
