package com.khs.mapper;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.khs.dto.Cre;
import com.khs.dto.ReplyDB;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class Replymappertest {

	
	private final static Logger log = LoggerFactory.getLogger("Replymappertest.class");
	
	@Autowired
	private Replymapper r;
	
	
	@Test
	public void test() {
		log.info(r.toString());;
	}
	
	@Test
	public void test1() {
		Cre c = new Cre(1,5);
		List<ReplyDB> reply = r.list("1", c);
		for(ReplyDB rn : reply) {
			log.info("댓글내용:"+rn.getContent());
		}
	}
	//Long 타입은 테스트시 뒤에 L을 붙여야함 ex)Long seqno = 7L;
	@Test
	public void test2() {
		ReplyDB re = new ReplyDB();
		re.setSeqno("24");
		re.setContent("댓글재수정확인");
		log.info("업데이트 확인 : "+r.update(re));
	}
}
