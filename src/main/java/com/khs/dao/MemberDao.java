package com.khs.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.khs.dto.Member;

public interface MemberDao {
	
	public Map<Integer , String> login(String id,String pw);
	
	public int insert(Member member);
	
	public int check(String id);
	
	public List<Member> list(HttpServletRequest req);
	
}
