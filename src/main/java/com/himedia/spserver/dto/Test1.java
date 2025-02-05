package com.himedia.spserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test1 {
    private int id;
    private String name;
    private int age;
    @Builder.Default
    private String address = "대한민국 서울시";
}
