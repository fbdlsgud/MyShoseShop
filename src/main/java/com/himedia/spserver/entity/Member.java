package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
// @ToString(exclude = "memberRoleList")  // ToString 출력내용중 memberRoleList 제외
public class Member {
    @Id @Column(length = 50)
    private String userid;
    @Column(length = 300)
    private String pwd;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 100)
    private String email;
    @Column(length = 50)
    private String phone;
    @Column(length = 50)
    private String zip_num;
    @Column(length = 100)
    private String address1;
    @Column(length = 100)
    private String address2;
    @Column(length = 100)
    private String address3;
    @Column( columnDefinition="DATETIME default now()" )
    private Timestamp indate;
    @Column(length = 50)
    private String provider;
    @Column(length = 50)
    private String snsid;

    @ElementCollection(fetch = FetchType.LAZY)  // 테이블의 리스트가 아니라 단순데이터(String, Integer 등)이라고 MySQL 에 알려주는 어너테이션
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<MemberRole>();

}
