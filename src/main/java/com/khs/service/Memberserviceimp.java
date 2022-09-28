package com.khs.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khs.dao.MemberDaoimp;
import com.khs.dto.Member;

@Service
public class Memberserviceimp implements MemberService {
	
	@Autowired
	private MemberDaoimp m;
	
	@Override
	public  Map<Integer , String> login(String id, String pw) {
		return m.login(id, pw);
	}

	@Override
	public int memReg(Member member) {
		return m.insert(member);
	}
	@Override
	public int check(String id) {
		return m.check(id);
	}
	@Override
	public List<Member> list(HttpServletRequest req) {
		return m.list(req);
	}

}
