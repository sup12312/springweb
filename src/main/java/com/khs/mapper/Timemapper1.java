package com.khs.mapper;

import org.apache.ibatis.annotations.Select;

public interface Timemapper1 {

	
	@Select("Select sysdate FROM dual")
	public String gettime();
}
