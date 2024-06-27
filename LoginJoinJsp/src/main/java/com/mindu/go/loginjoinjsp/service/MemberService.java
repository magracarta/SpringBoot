package com.mindu.go.loginjoinjsp.service;

import com.mindu.go.loginjoinjsp.dao.MemberDao;
import com.mindu.go.loginjoinjsp.dto.KakaoProfile;
import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import com.mindu.go.loginjoinjsp.dto.MemberVO;
import com.mindu.go.loginjoinjsp.dto.NaverApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;
    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberDTO getMember(String userid) {
        return memberDao.getMember(userid);
    }

    public MemberDTO findByUsername(Object username) {
        return memberDao.getMember((String) username);
    }

    public HashMap<String, Object> getMemberList() {
        HashMap<String , Object> list = new HashMap<>();
        List<MemberVO> mvoList = memberDao.getMemberList();
        list.put("memberList", mvoList);
        return list;
    }

    public String getcertifcaion(String userid) {
        return memberDao.getCode(userid);
    }

    public void insertMember(MemberDTO memberdto) {
        memberDao.insertMember(memberdto);
    }

    public int insertSnsMember(KakaoProfile kakaoProfile) {
        return memberDao.insertSnsMember(kakaoProfile);
    }

    public MemberVO getKakaMember(KakaoProfile kakaoProfile) {

        MemberVO kdto = memberDao.getKakaMember(String.valueOf(kakaoProfile.getId()));
        return kdto;
    }

    public MemberVO getNaverMember(NaverApi naverApi) {
        MemberVO Ndto = memberDao.getNaverMember(naverApi);
        return Ndto;
    }

    public int insertNaverMember(NaverApi naverApi) {
        return memberDao.insertNaverMember(naverApi);
    }
}
