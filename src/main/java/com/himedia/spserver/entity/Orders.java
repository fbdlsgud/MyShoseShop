package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int oseq;

    @Column( columnDefinition="DATETIME default now()" )
    @CreationTimestamp
    private Timestamp indate;

    @ManyToOne
    @JoinColumn(name = "member_userid")
    Member member;
}
