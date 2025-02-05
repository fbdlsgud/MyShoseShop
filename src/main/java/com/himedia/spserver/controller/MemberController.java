package com.himedia.spserver.controller;

import com.google.gson.Gson;
import com.himedia.spserver.dto.KakaoProfile;
import com.himedia.spserver.dto.OAuthToken;
import com.himedia.spserver.entity.Member;
import com.himedia.spserver.security.util.CustomJWTException;
import com.himedia.spserver.security.util.JWTUtil;
import com.himedia.spserver.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService ms;

    @GetMapping("/")
    public String index() {
        return "<h1>Hello  JPA SECURITY World</h1>";
    }

    @PostMapping("/idcheck")
    public HashMap<String, String> idcheck(@RequestParam("userid") String userid) {
        HashMap<String, String> result = new HashMap<>();
        Member member = ms.getMember( userid );
        if( member != null )  result.put("msg", "not_ok");
        else  result.put("msg", "ok");
        return result;
    }


    @PostMapping("/join")
    public HashMap<String, String> join(@RequestBody Member member) {
        HashMap<String, String> result = new HashMap<>();
        ms.insertMember(member);
        result.put("msg", "ok");
        return result;
    }


    @GetMapping("/refresh")
    public HashMap<String, String> refresh(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("refreshToken") String refreshToken
    ) throws CustomJWTException {
        HashMap<String, String> result = new HashMap<>();

        if( refreshToken == null ) throw  new CustomJWTException("NULL_REFRESH");
        if( authHeader == null || authHeader.length() < 7 ) throw new CustomJWTException("INVALID_HEADER");

        String accessToken = authHeader.substring(7);

        // 기간 만료이면 false  , 만료이전이면  true 리턴
        boolean expiredResult = checkExpiredToken( accessToken );

        if( expiredResult ) {
            System.out.println("토큰 유료기간 만료전... 계속 사용");
            result.put("accessToken", accessToken);
            result.put("refreshToken", refreshToken);
        }else{
            System.out.println("토큰 교체");
            // refreshToken 을 validate 하면서 저장된 사용자정보를 추출
            Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
            // 추출한 사용자정보를 이용하여 새로운  access 토큰을 만듭니다
            String newAccessToken = JWTUtil.generateToken(claims, 1);
            String newRefreshToken = "";
            if(  checkTime( (Integer)claims.get("exp") )  )
                newRefreshToken = JWTUtil.generateToken(claims, 60*24);
            else
                newRefreshToken = refreshToken;

            result.put("accessToken", newAccessToken);
            result.put("refreshToken", newRefreshToken);
        }
        return result;
    }
    private boolean checkTime(Integer exp) {
        java.util.Date expDate = new java.util.Date( (long)exp * (1000 ));//밀리초로 변환
        long gap = expDate.getTime() - System.currentTimeMillis();//현재 시간과의 차이 계산
        long leftMin = gap / (1000 * 60); //분단위 변환
        return leftMin < 60;  // 한시간 미만으로 남았으면  true 그렇지 않으면 false 가 리턴
    }

    private boolean checkExpiredToken(String accessToken) {
        try {
            JWTUtil.validateToken( accessToken );
        } catch (CustomJWTException e) {
            if( e.getMessage().equals("Expired") ) return false;
        }
        return true;

    }


    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + client_id + "&"
                + "redirect_uri=" + redirect_uri + "&"
                + "response_type=code';" + "</script>";
        return a;
    }


    @RequestMapping("/kakaoLogin")
    public void kakaoLogin(HttpServletRequest request, HttpServletResponse response ) throws IOException, MalformedURLException {
        String code = request.getParameter("code");
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);
        String bodyData = "grant_type=authorization_code&";
        bodyData += "client_id=" + client_id + "&";
        bodyData += "redirect_uri=" + redirect_uri + "&";
        bodyData += "code=" + code;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input = "";
        StringBuilder sb = new StringBuilder();
        while ((input = br.readLine()) != null) {
            sb.append(input);
        }
        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);

        HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
        String input2 = "";
        StringBuilder sb2 = new StringBuilder();
        while ((input2 = br2.readLine()) != null) {
            sb2.append(input2);
            System.out.println(input2);
        }
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println("id : " + kakaoProfile.getId());
        System.out.println("KakaoAccount-Email : " + ac.getEmail());
        System.out.println("Profile-Nickname : " + pf.getNickname());

        Member member = ms.getMember( kakaoProfile.getId() );
        if( member == null ){
            member = new Member();
            member.setUserid( kakaoProfile.getId() );
            member.setName( pf.getNickname() );
            member.setPwd( "kakao" );
            member.setProvider( "kakao" );
            ms.insertMember(member);
        }
        HttpSession session = request.getSession();
        response.sendRedirect("http://localhost:3000/kakaoIdLogin/"+member.getUserid());
    }


    @PostMapping("/updateMember")
    public HashMap<String, Object> updateMember(@RequestBody Member member) {
        HashMap<String, Object> result = new HashMap<>();
        Member updateMember = ms.updateMember(member);
        result.put("updateMember", updateMember);
        result.put("msg", "ok");
        return result;
    }



    @PostMapping("/withDrawal")
    public HashMap<String, Object> withDrawal(@RequestParam("userid") String userid) {
        HashMap<String, Object> result = new HashMap<>();
        ms.deleteMember( userid );
        result.put("msg", "ok");
        return result;
    }


}
