package com.mindu.go.loginjoinjsp.dao;

import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import com.mindu.go.loginjoinjsp.dto.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberDao {
    public MemberDTO getMember(String userid);


    List<MemberVO> getMemberList();

    String getCode(String userid);

    void insertMember(MemberDTO memberdto);
}
