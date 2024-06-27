package com.mindu.go.loginjoinjsp.dto;

import lombok.Data;

@Data

public class NaverApi {
    private String resultcode;
    private String message;
    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;
        private String email;
        private String name;
    }
}
