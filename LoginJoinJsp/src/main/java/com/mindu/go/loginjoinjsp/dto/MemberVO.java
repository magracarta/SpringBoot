package com.mindu.go.loginjoinjsp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String userid;
    private String name;
    private Timestamp birth;
    private String email;
    private String provider;
    private Timestamp createdate;
    private String image;
    private  String saveimage;
    private String zip_code;
    private String address1;

    private String address2;
    private String address3;

}
