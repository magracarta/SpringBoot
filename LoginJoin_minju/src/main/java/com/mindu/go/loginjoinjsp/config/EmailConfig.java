package com.mindu.go.loginjoinjsp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig{
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //java mailsender 인터페이스르 구현한 객체를 빈으로 등록
        mailSender.setHost("smtp.gmail.com"); //이메일 전송에 사용할 smtp 서버 호스트를 설정
        mailSender.setPort(587);
        mailSender.setUsername("rmfoal1996@gmail.com");
        mailSender.setPassword("dapj huuq euze hrxc");//구글 앱 비밀번호

        Properties javaMailProperties = new Properties();//javamail의 속성을 설정하기 위해 properties 객체를 생성
        javaMailProperties.put("mail.transport.protocol", "smtp"); //프로토콜로 smtp 사용
        javaMailProperties.put("mail.smtp.auth", "true"); //smtp 서버에 인증이 필요
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//SSL 소캣 팩토리 클래스 사용
        javaMailProperties.put("mail.smtp.starttls.enable", "true");//STARTTLS(TLS를 시작하는 명령)를 사용하여 암호화된 통신을 활성화
        javaMailProperties.put("mail.debug", "true");//디버깅 정보 출력
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");//smtp 서버의 ssl 인증서를 신뢰
        javaMailProperties.put("mail.smtp.ssl.protocols","TLSv1.2");//사용할 ssl 프로토콜 버전

        mailSender.setJavaMailProperties(javaMailProperties);//mailSender에 만든 propertie 전송

        return mailSender;

    }
}
