package com.mindu.go.loginjoinjsp.util;

import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Repository
public class Utillity {
    public String sha256(String pass , String salt){
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            md.update(salt.getBytes());
            byte[] data = md.digest();
            for (byte b: data){
                sb.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();

    }

    public String saltmake(){
        String salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];
            sr.nextBytes(bytes);
            salt = new String(Base64.getEncoder().encode(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
