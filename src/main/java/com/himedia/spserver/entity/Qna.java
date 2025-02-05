package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Data
public class Qna {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer qseq;

    @Column(nullable = false, length = 100)
    private String subject;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(length = 500)
    private String reply;

    @ManyToOne
    @JoinColumn(name = "member_userid")
    Member member;

    @Column( columnDefinition="DATETIME default now()" )
    private Timestamp indate;

    @Column(nullable = false, length = 5) @ColumnDefault("'N'")
    private String security;

    @Column(length = 100)
    private String pass;

}
