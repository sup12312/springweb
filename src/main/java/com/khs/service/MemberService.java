package com.khs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.khs.dto.Member;



public interface MemberService {
	 Map<Integer , String> login(String id, String pw);
	int memReg(Member member);
	public int check(String id);
	List<Member> list(HttpServletRequest req);
}
