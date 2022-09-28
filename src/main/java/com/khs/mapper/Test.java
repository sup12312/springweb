package com.khs.mapper;

import org.apache.ibatis.annotations.Select;

public interface Test {
	
	
	@Select("SELECT sysdate FROM dual")
	public String gettime();
}
