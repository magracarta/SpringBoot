package com.mindu.go.loginjoinjsp.dto;

import lombok.Data;

@Data
public class MemberEmailDto {
    private int id;
    private String userid;
    private String vcode;
    private String etime;
}
