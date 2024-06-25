package com.mindu.go.loginjoinjsp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data

public class MemberDTO {
    private String userid;
    private String pass;
    private String name;
    private Timestamp birth;
    private String email;
    private String provider;
    private Timestamp createdate;

}
