package com.mindu.go.loginjoinjsp.controller;

import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import com.mindu.go.loginjoinjsp.dto.MemberVO;
import com.mindu.go.loginjoinjsp.service.MemberService;
import com.mindu.go.loginjoinjsp.util.Utillity;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberRestController {
    @Autowired
    MemberService ms;
    @Autowired
    Utillity ut;

    @PostMapping("login")
    public HashMap<String, Object> login(@ModelAttribute("memberdto") MemberDTO memberdto, HttpSession session) {
        HashMap<String, Object> list = new HashMap<>();
        MemberDTO mvo = ms.getMember(memberdto.getUserid());
        String salt = "";
        if (mvo != null)salt =  mvo.getSalt();
        if (mvo == null) {
            list.put("loginCheck",-2);
        }else if( !ut.sha256(memberdto.getPass() , salt).equals(mvo.getPass())){
            list.put("loginCheck",-1);
        }else if(ut.sha256(memberdto.getPass() , salt).equals(mvo.getPass()) ){
            list.put("loginCheck",1);
            MemberVO logindata = new MemberVO(
                    mvo.getUserid(),mvo.getName(),mvo.getBirth(), mvo.getEmail(), mvo.getProvider() , mvo.getCreatedate()
            );
            session.setAttribute("loginUser", logindata);
        }
        return list;
    }


    @GetMapping("/session")
    public Map<String, Object> session(HttpSession session) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("loginUser", session.getAttribute("loginUser"));
        return sessionData;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser"); // "loginUser" 세션 속성 제거
        session.invalidate(); // 세션 전체 무효화
        return "로그아웃 완료";
    }

}
