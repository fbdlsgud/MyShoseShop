package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Data
@DynamicInsert  // insert 할때 없는값(null) 은 제외하고  insert 함
@DynamicUpdate   // update 할때 없는값(null) 은 제외하고  update 함
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int pseq;
    @Column( length = 50, nullable = false)
    private String name;
    @Column( nullable = false)
    private int category;
    private int price1;
    @Column( nullable = false)
    private int price2;
    private int price3;
    @Column( length = 1000, nullable = false)
    private String content;
    @Column( nullable = false)
    private String image;
    @Column( nullable = false)
    private String savefilename;
    @Column( length = 5)  @ColumnDefault("'N'")
    private String bestyn;
    @Column( length = 5)   @ColumnDefault("'Y'")
    private String useyn;
    @Column( columnDefinition="DATETIME default now()" )
    private Timestamp indate;
}

// unique : unique 제약설정
// name : 테이블내의 필드명 지정
// columnDefinition : SQL 에서 사용하는 정의문을 직접 지정
// 예 : @Column(columnDefinition = "varchar(100) default 'EMPTY'")
