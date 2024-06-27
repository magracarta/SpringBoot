package com.mindu.go.loginjoinjsp.controller;

import com.mindu.go.loginjoinjsp.dto.MemberDTO;
import com.mindu.go.loginjoinjsp.dto.MemberVO;
import com.mindu.go.loginjoinjsp.service.MemberService;
import com.mindu.go.loginjoinjsp.service.SNSLoginService;
import com.mindu.go.loginjoinjsp.util.Utillity;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberRestController {
    private final MemberService memberService;
    private final Utillity ut;
     private final ServletContext sct;
     private final SNSLoginService sc;
    @Autowired
    public MemberRestController(MemberService memberService, Utillity ut, ServletContext sct, SNSLoginService sc) {
        this.memberService = memberService;
        this.ut = ut;
        this.sct = sct;
        this.sc = sc;
    }

    @PostMapping("login")
    public HashMap<String, Object> login(@ModelAttribute("memberdto") MemberDTO memberdto, HttpSession session) {
        HashMap<String, Object> list = new HashMap<>();
        MemberDTO mvo = memberService.getMember(memberdto.getUserid());
        String salt = "";
        if (mvo != null)salt =  mvo.getSalt();
        if (mvo == null) {
            list.put("loginCheck",-2);
        }else if( !ut.sha256(memberdto.getPass() , salt).equals(mvo.getPass())){
            list.put("loginCheck",-1);
        }else if(ut.sha256(memberdto.getPass() , salt).equals(mvo.getPass()) ){
            list.put("loginCheck",1);
            MemberVO logindata = new MemberVO(
                    mvo.getUserid(),mvo.getName(),mvo.getBirth(), mvo.getEmail(), mvo.getProvider() , mvo.getCreatedate() , mvo.getImage() , mvo.getSaveimage(),
                    mvo.getZip_code(), mvo.getAddress1(), mvo.getAddress2(), mvo.getAddress3());
            session.setAttribute("loginUser", logindata);
        }
        return list;
    }


    @GetMapping("/session")
    public Map<String, Object> session(HttpSession session) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("loginUser", session.getAttribute("loginUser"));
        return sessionData;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if(loginUser.getProvider()!= null && loginUser.getProvider().equals("kakao")){
            sc.kakaoLogout(session);
        }

        session.removeAttribute("loginUser"); // "loginUser" 세션 속성 제거
        session.invalidate(); // 세션 전체 무효화
        return "로그아웃 완료";
    }

    @GetMapping("/memberlist")
    public HashMap<String , Object> memberList() {
        HashMap<String , Object> list = memberService.getMemberList();
        return list;
    }

    @RequestMapping("/join")
    public HashMap<String , Object> join(@ModelAttribute("memberdto") @Valid MemberDTO memberdto, BindingResult result  ,
         @RequestParam("passCheck") String passCheck  , @RequestParam(value = "birthdate" ,required = false) String birthdate  ,@RequestParam("idcheck") String idcheck  , @RequestParam("file") MultipartFile file , @RequestParam("vcode") String vcode, HttpSession session ) throws IllegalAccessException {
        boolean validCheck = true;
        HashMap<String , Object> list = new HashMap<>();
        if(birthdate!= null){
            LocalDate ld = LocalDate.parse(birthdate);
            Timestamp birth = Timestamp.valueOf(ld.atStartOfDay());
            memberdto.setBirth(birth);
        }
        for(Field f : memberdto.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(result.getFieldError(f.getName()) != null){
                list.put("message",result.getFieldError(f.getName()).getDefaultMessage());
                validCheck = false;
                break;
            }
            if(f.getName().equals("userid") ){
                if(!f.get(memberdto).equals(idcheck)){
                    list.put("message","아이디 중복체크를 해주세요.");
                    validCheck = false;
                    break;
                }
            }
            if(f.getName().equals("pass") ){
                if(!f.get(memberdto).equals(passCheck)){
                    list.put("message","비밀번호체크를 확인 해주세요.");
                    validCheck = false;
                    break;
                }
            }
        }

        if (vcode.isEmpty()) {
            list.put("message","이메일 인증을 확인해주세요.");
            validCheck = false;
        }else{
            if(session.getAttribute("emailCode") == null) {
                list.put("message","이메일 인증이 만료되었습니다. 다시 시도해주세요.");
                validCheck = false;
            }else if(vcode.equals(memberService.getcertifcaion(memberdto.getUserid()))){
                validCheck = true;
            }
        }

        //이미지 추가~~
        if(!file.isEmpty()){
            String filePath = sct.getRealPath("/images");
            LocalDateTime ldt = LocalDateTime.now();
            String datastring = ldt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            String fileName = file.getOriginalFilename();

            String f1 = fileName.substring(0,fileName.indexOf("."));
            String f2 = fileName.substring(fileName.indexOf("."));

            String saveFilename = f1 + datastring + f2;

            try {
                file.transferTo(new File(filePath + File.separator + saveFilename));
                memberdto.setImage(fileName);
                memberdto.setSaveimage(saveFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(validCheck){
            //인서트문 추가~
            String salt = ut.saltmake();
            System.out.println(salt);
            memberdto.setPass(
                    ut.sha256(memberdto.getPass(), salt)
            );
            memberdto.setSalt(salt);
            memberService.insertMember(memberdto);
            list.put("joinst" , 1);
        }
        return list;
    }


    @PostMapping("/idcheck")
    public int idcheck(@RequestParam("userid") String userid){
        MemberDTO mvo = memberService.getMember(userid);
        int result = 1;
        if(mvo != null){
            result=0;
        }
        return result;
    }


}
