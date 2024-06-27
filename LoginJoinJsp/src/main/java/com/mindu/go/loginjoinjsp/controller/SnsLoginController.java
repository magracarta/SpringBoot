package com.mindu.go.loginjoinjsp.controller;

import com.mindu.go.loginjoinjsp.dto.*;
import com.mindu.go.loginjoinjsp.eception.KakaoEceiption;
import com.mindu.go.loginjoinjsp.eception.NaverException;
import com.mindu.go.loginjoinjsp.service.MemberService;
import com.mindu.go.loginjoinjsp.service.SNSLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
@RestController
public class SnsLoginController {
    private final SNSLoginService snsLoginService;
    private final MemberService memberService;

    @RequestMapping("kakaoLogin")
    public int kakaoLogin(@RequestParam("code") String code , HttpSession session) {
        System.out.println(code);
        int result = 0;
        OAuthToken oAuthToken = snsLoginService.getKakaoToken(code);
        KakaoProfile kakaoProfile = snsLoginService.getKakaoProfile(oAuthToken);

        try {
            if(kakaoProfile == null){
                throw new KakaoEceiption("카카오 로그인 실패","oAutoToken도 같이 확인해주세요.");
            }

            //로그인 여부 확인
            if(memberService.getKakaMember(kakaoProfile) == null ) result =  memberService.insertSnsMember(kakaoProfile);
            else result =2;
            System.out.println(kakaoProfile);
            MemberVO member = memberService.getKakaMember(kakaoProfile);
            session.setAttribute("loginUser", member);
            session.setAttribute("kakaoToken", oAuthToken.getAccess_token());
        }catch (KakaoEceiption e) {
            e.printStackTrace();
            System.out.println(e.getKakaodata());
        }


        return result;
    }
    @RequestMapping("/naverLogin")
    public int naverLogin(@RequestParam("code") String code ,@RequestParam("state") String state , HttpSession session) {
        int result = 0;
        OAuthToken oAuthToken = snsLoginService.getNaverToken(code , state);

        //NaverApi
        NaverApi naverApi = snsLoginService.getLoginAPI(oAuthToken.getAccess_token());
        try {
            if(naverApi == null)throw new NaverException("네이버 로그인 실패");
            if( memberService.getNaverMember(naverApi) == null)  result = memberService.insertNaverMember(naverApi);
            else result =2;

            MemberVO member = memberService.getNaverMember(naverApi);
            session.setAttribute("loginUser", member);

        } catch (NaverException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result;
    }

}
