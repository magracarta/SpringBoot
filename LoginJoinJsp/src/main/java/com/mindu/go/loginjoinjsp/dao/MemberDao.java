package com.mindu.go.loginjoinjsp.dao;

import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public MemberDTO getMember(String userid);
}
