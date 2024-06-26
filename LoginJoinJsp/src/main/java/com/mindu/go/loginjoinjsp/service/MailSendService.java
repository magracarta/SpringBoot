package com.mindu.go.loginjoinjsp.service;

import com.mindu.go.loginjoinjsp.dao.IMemberEmailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailSendService {

    private final  JavaMailSender mailSender;
    private final  IMemberEmailDao imdao;
    @Autowired
    public MailSendService(JavaMailSender mailSender, IMemberEmailDao imdao) {
        this.mailSender = mailSender;
        this.imdao = imdao;
    }

    private int authNumber;

    //임의의 6자리 양수를 반환
    public void makeRandomNumber(){
        Random r = new Random();
        String randomNumber= "";
        for(int i =0; i<6; i++){
            randomNumber += Integer.toString(r.nextInt(10));
        }
        authNumber = Integer.parseInt(randomNumber);
    }

    //mail을 어디서 보내는지, 어디로 보내는지, 인증 번호를 html 형식으로 어떻게 보내는지 작성
    public String joinEmail(String email){
        makeRandomNumber();
        String setFrom = "rmfoal1996@gmail.com";
        String toMail = email;
        String title ="회원가입 인증 이메일 입니다."; //이메일 제목
        String content ="로그인사이트를 방문해주셔서 감사합니다. " +
                "<br><br>" +
                "인증 번호는" + authNumber+"입니다."+"<br>"+
                "인증번호를 입력해주세요.";
        mailSend(setFrom, toMail,title,content);
        return Integer.toString(authNumber);
    }

    public void mailSend(String setFrom, String toMail, String title, String content){
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여
        //MimeMessage 객체를 생성
        try {
            //이메일 메시지와 관련된 설정을 수행합니다.
            MimeMessageHelper helper = new MimeMessageHelper(message, true , "UTF-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            //이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public int insertEmail(String vcode, String userid) {

        return imdao.insertEmail(vcode, userid);
    }
}
