package com.mindu.go.loginjoinjsp.controller;

import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import com.mindu.go.loginjoinjsp.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
    @Autowired
    MemberService ms;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public ModelAndView login(@ModelAttribute("memberdto") MemberDTO memberdto) {
        ModelAndView mav = new ModelAndView();
        MemberDTO mvo = ms.getMember(memberdto.getUserid());
        if (mvo == null) {
            mav.addObject("message","계정이 존재하지 않습니다.");
            mav.setViewName("/");
            return mav;
        }
        if( !memberdto.getUserid().equals(mvo.getUserid()) ){
            mav.addObject("message","아이디를 확인해주세요.");
            mav.setViewName("/");
        }else if( memberdto.getPass().equals(mvo.getPass()) ){
            mav.setViewName("memberList");
        }


        return mav;
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "joinForm";
    }

}
