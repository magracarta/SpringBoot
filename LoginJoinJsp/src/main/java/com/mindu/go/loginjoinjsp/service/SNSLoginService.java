package com.mindu.go.loginjoinjsp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindu.go.loginjoinjsp.dto.KakaoProfile;
import com.mindu.go.loginjoinjsp.dto.NaverApi;
import com.mindu.go.loginjoinjsp.dto.OAuthToken;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SNSLoginService {
    
    //카카오
    public OAuthToken getKakaoToken(String code) {
        OAuthToken token = new OAuthToken();
        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code").append("&client_id=7a06fabd130063b1612dc2388af35e0e")
                    .append("&redirect_uri=http://localhost:8060/kakaoLogin.html").append("&code="+code);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(sb.toString());
            bw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sbkey = new StringBuilder();
            String input = "";
            while ((input=br.readLine())!= null){
                sbkey.append(input);
                System.out.println(input);
            }

            ObjectMapper mapper = new ObjectMapper();
            token = mapper.readValue(sbkey.toString(), OAuthToken.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get Kakao OAuth token", e);
        }

        return token;
    }

    public KakaoProfile getKakaoProfile(OAuthToken oAuthToken) {
        KakaoProfile kakaoProfile = null;
        try {
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+oAuthToken.getAccess_token());
            String line = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while ((line=br.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            kakaoProfile = mapper.readValue(sb.toString(), KakaoProfile.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(kakaoProfile));


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get Kakao profile", e );
        }
        return kakaoProfile;
    }

    public void kakaoLogout(HttpSession session) {
        String oAuthToken = (String) session.getAttribute("kakaoToken");
        if (oAuthToken == null) {
            throw new RuntimeException("failed to get OAuth token");
        }
        try {
            URL url = new URL("https://kapi.kakao.com/v1/user/logout");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Authorization","Bearer "+oAuthToken);
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write("");
            writer.flush();
            writer.close();

            //응답 확인
            int responseCode = conn.getResponseCode();
            if(responseCode ==200){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream() , "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                System.out.println("Logout response: " + sb.toString());

                // 세션 정리
                session.removeAttribute("kakaoToken");

            } else {
                throw new RuntimeException("Failed to logout from Kakao. HTTP response code: " + responseCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public OAuthToken getNaverToken(String code , String state) {
        OAuthToken oAuthToken = null;
        try {
            URL url = new URL("https://nid.naver.com/oauth2.0/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code").append("&client_id=91PQCpHwsJKS6Ni5owXS").append("&client_secret=wNH8RZ5hit")
                    .append("&redirect_uri=http://localhost:8060/naverlogin.html").append("&code=").append(code)
                    .append("&state=").append(state);


            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer sbkey = new StringBuffer();
            String input ="";
            while ((input=br.readLine())!= null){
                sbkey.append(input);
            }
            br.close();

            System.out.println(sbkey.toString());

            ObjectMapper mapper = new ObjectMapper();
            oAuthToken = mapper.readValue(sbkey.toString(), OAuthToken.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return oAuthToken;
    }

    public NaverApi getLoginAPI(String accessToken) {
        NaverApi naverApi =null;
        try {
            URL url = new URL("https://openapi.naver.com/v1/nid/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+accessToken);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            naverApi = mapper.readValue(sb.toString(), NaverApi.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(naverApi));


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return naverApi;
    }
}
