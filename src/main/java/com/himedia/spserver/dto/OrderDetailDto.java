package com.himedia.spserver.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDetailDto {
    private int odseq;
    private int oseq;
    private String userid;
    private Timestamp indate;
    private int pseq;
    private int quantity;
    private String mname;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    private String phone;
    private String pname;
    private int price2;
    private int result;
}
