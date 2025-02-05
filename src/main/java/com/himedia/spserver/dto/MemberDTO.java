package com.himedia.spserver.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class MemberDTO extends User {
    public MemberDTO(
            String username,
            String password,
            String name,
            String email,
            String phone,
            //Timestamp indate,
            String zip_num,
            String address1,
            String address2,
            String address3,
            String provider,
            String snsid,
            List<String> roleNames ) {
                super(username, password,
                    roleNames.stream().map(
                        str -> new SimpleGrantedAuthority("ROLE_"+str)
                ).collect(Collectors.toList())   // List의 내용들에  ROLE_ 를 앞에 붙여서 다시 리스트로 구성
            );
            this.userid = username;
            this.pwd = password;
            this.name = name;
            this.email = email;
            this.phone=phone;
            this.zip_num=zip_num;
            //this.indate = indate;
            this.address1=address1;
            this.address2=address2;
            this.address3=address3;
            this.provider=provider;
            this.snsid=snsid;
            this.roleNames = roleNames;
    }
    private String userid;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    //private Timestamp indate;
    private String provider;
    private String snsid;
    private List<String> roleNames = new ArrayList<String>();

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userid", userid);
        dataMap.put("pwd",pwd);
        dataMap.put("name",name);
        dataMap.put("email", email);
        dataMap.put("phone", phone);
        dataMap.put("zip_num", zip_num);
        dataMap.put("address1", address1);
        dataMap.put("address2", address2);
        dataMap.put("address3", address3);
        //dataMap.put("indate", indate);
        dataMap.put("provider", provider);
        dataMap.put("snsid", snsid);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }
}
