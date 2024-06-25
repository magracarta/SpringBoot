package com.mindu.go.loginjoinjsp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utillity {
    public String sha256(String pass , String salt){
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] data = md.digest();
            for (byte b: data){
                sb.append(String);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
