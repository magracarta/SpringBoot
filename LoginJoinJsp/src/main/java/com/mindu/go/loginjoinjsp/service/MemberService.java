package com.mindu.go.loginjoinjsp.service;

import com.mindu.go.loginjoinjsp.dao.MemberDao;
import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberDao md;

    public MemberDTO getMember(String userid) {
        return md.getMember(userid);
    }

    public MemberDTO findByUsername(Object username) {
        return md.getMember((String) username);
    }
}
