package com.jmk.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeMapper {

		@Select("SELECT now() from dual;")
		public String getTime();
}
