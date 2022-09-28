package com.khs.common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

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
public class DBTEST {

	
	private final static Logger log = LoggerFactory.getLogger("DBTEST2.class");
	@Autowired
	private DataSource dbconn;
	
	@Test
	public void test() {
			Connection conn;
			try {
				conn = dbconn.getConnection();
				log.info(conn.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
