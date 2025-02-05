package com.himedia.spserver.security.filter;

import com.google.gson.Gson;
import com.himedia.spserver.dto.MemberDTO;
import com.himedia.spserver.security.util.JWTUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class JWTCheckFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderStr = request.getHeader("Authorization");
        try {
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            System.out.println("JWT claims: " + claims);

            String userid=(String)claims.get("userid");
            String pwd = (String) claims.get("pwd");
            String name = (String) claims.get("name");
            String phone = (String) claims.get("phone");
            String email = (String) claims.get("email");
            String zip_num = (String) claims.get("zip_num");
            String address1 = (String) claims.get("address1");
            String address2 = (String) claims.get("address2");
            String address3 = (String) claims.get("address3");
            //Timestamp indate = (Timestamp) claims.get("indate");
            String provider = (String) claims.get("provider");
            String snsid = (String) claims.get("snsid");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO( userid, pwd, name,  email,  phone, zip_num,  address1,  address2,   address3,   provider,  snsid,  roleNames  );
            System.out.println("2");
            System.out.println("-----------------------------------");
            System.out.println(memberDTO);
            System.out.println(memberDTO.getAuthorities()); // 권한 추출

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO, pwd , memberDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("3");
            filterChain.doFilter(request, response);

        }catch(Exception e){
            System.out.println("JWT Check Error..............");
            System.out.println(e.getMessage());
            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        // 최초 요청은 이곳에서  토큰점검없이 통과할지 여부를 점검하고
        // 점검할 요청은 doFilterInternal 메서드로 이동하고
        // 그냥 통과할 요청은 바로 해당 Mapping 메서드로 이동합니다

        // 엑세스 토큰 검사 없이 통과시킬 요청을 설정
        String path = request.getRequestURI();
        System.out.println("check uri : " + path);

        if(request.getMethod().equals("OPTIONS"))
            return true;

        if(path.startsWith("/member/login"))  // -> security formLogin의 loginPage 로 이동
            return true;

        if(path.startsWith("/product/getBestProduct"))
            return true;

        if(path.startsWith("/product/getNewProduct"))
            return true;

        if(path.startsWith("/images/"))
            return true;

        if(path.startsWith("/product_images/"))
            return true;

        if(path.startsWith("/member/idcheck"))
            return true;

        if(path.startsWith("/member/join"))
            return true;

        if(path.startsWith("/member/sendMail"))
            return true;

        if(path.startsWith("/member/codecheck"))
            return true;

        if(path.startsWith("/member/kakaostart"))
            return true;


        if(path.startsWith("/member/kakaoLogin"))
            return true;

        if(path.startsWith("/product/categoryList"))
            return true;

        if(path.startsWith("/product/getProduct"))
            return true;


        if(path.startsWith("/member/refresh"))
            return true;

        if(path.startsWith("/favicon.ico"))
            return true;

        if(path.startsWith("/product/categoryList"))
            return true;

        if(path.startsWith("/product/getProduct"))
            return true;

        return false;
    }
}
