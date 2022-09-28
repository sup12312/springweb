package com.khs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.ReplyDB;
import com.khs.dto.Replylist;
import com.khs.mapper.Replymapper;

@Service
public class ReplyServiceimp implements ReplyService {
	
	private static final Logger log = LoggerFactory.getLogger("RplyServiceimp.class");
	
	@Autowired
	private Replymapper mapper;
	
	@Override
	public int replyinsert(Reply r) {
		log.info("reply insert service called..");
		return mapper.insert(r);
	}
	
	@Override
	public List<ReplyDB> list(String no, Cre c){
		return mapper.list(no,c);
	}

	@Override
	public ReplyDB detail(int no) {
		return mapper.detail(no);
	}

	@Override
	public int update(ReplyDB re) {
		return mapper.update(re);
	}

	@Override
	public int delete(int no) {
		return mapper.delete(no);
	}

	@Override
	public Replylist getlist(String no, Cre c) {
		Replylist r = new Replylist(mapper.list(no,c),mapper.total(Integer.parseInt(no)));
		return r;
	}

}
