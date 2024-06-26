package com.mindu.go.loginjoinjsp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMemberEmailDao {
    int insertEmail(@Param("vcode") String vcode, @Param("userid") String userid);
}
