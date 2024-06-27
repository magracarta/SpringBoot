package com.mindu.go.loginjoinjsp.controller;

import com.mindu.go.loginjoinjsp.dto.EmailRequestDto;
import com.mindu.go.loginjoinjsp.service.MailSendService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailSendService mailservice;

    @Autowired
    public MailController(MailSendService mailservice) {
        this.mailservice = mailservice;
    }

    @PostMapping("/mailSend")
    public String sendMail(@ModelAttribute("emaildto") @Valid EmailRequestDto edto, HttpSession session) {
        System.out.println("이메일 인증 이메일 : "+ edto.getEmail());
        session.setAttribute("emailCode", mailservice.joinEmail(edto.getEmail()));
        session.setMaxInactiveInterval(3*60);
        return "이메일 전송완료 3분 안에 이메일인증을 완료해주세요.";
    }

    @PostMapping("/mailCheck")
    public int checkMail( @RequestParam("vcode") String vcode , @RequestParam(value = "userid",required = false) String userid, HttpSession session) {
        String sessionCode = (String) session.getAttribute("emailCode");
        int result = -1;
        if(sessionCode.equals(vcode)) {
            result =  mailservice.insertEmail(vcode,userid);
        }
        return result;
    }
}
