package com.mindu.go.loginjoinjsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LoginJoinJspApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginJoinJspApplication.class, args);
    }

}
