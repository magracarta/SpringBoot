package com.mindu.go.loginjoinjsp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data

public class MemberDTO {
    @NotEmpty(message = "아이디를 입력해주세요.")
    @NotNull(message = "아이디를 입력해주세요.")
    private String userid;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String pass;
    private String salt;
    @NotEmpty(message = "이름을 입력해주세요.")
    @NotNull(message = "이름을 입력해주세요.")
    private String name;
    
    private Timestamp birth;
    @NotEmpty(message = "email을 입력해주세요.")
    @NotNull(message = "email을 입력해주세요.")
    private String email;
    private String provider;
    private Timestamp createdate;
    private String image;
    private  String saveimage;


}
