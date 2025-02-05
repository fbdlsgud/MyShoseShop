package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int cseq;
    // private String userid;
    // private int pseq;

    @Column( nullable = false )
    private int quantity;

    @Column( columnDefinition="DATETIME default now()" )
    @CreationTimestamp
    private Timestamp indate;

    @ManyToOne
    @JoinColumn(name = "member_userid")
    Member member;

    @ManyToOne
    @JoinColumn(name = "product_pseq")
    Product product;

}
