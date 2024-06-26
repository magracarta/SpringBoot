package com.mindu.go.loginjoinjsp.dto;

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

}
