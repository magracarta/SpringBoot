package com.mindu.go.loginjoinjsp.eception;

import lombok.Getter;

@Getter
public class KakaoEceiption extends NetworkEception{
    private final String kakaodata;

    public KakaoEceiption(String message, String kakaodata) {
        super(message);
        this.kakaodata = kakaodata;
    }

}
