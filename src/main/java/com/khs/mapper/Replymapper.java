package com.khs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.ReplyDB;

public interface Replymapper {
	
	public int insert(Reply r);
	
	//매개변수가 두개이상일 경우 지정을 해줘야함
	public List<ReplyDB> list(@Param("no") String no,@Param("c") Cre c);

	public ReplyDB detail(int no);

	public int update(ReplyDB re);

	public int delete(int no);
	
	public int total(int no);
}
