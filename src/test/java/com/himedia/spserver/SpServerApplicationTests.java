//package com.himedia.spserver;
//
//
//import com.himedia.spserver.dto.Test1;
//import com.himedia.spserver.entity.Member;
//import com.himedia.spserver.entity.MemberRole;
//import com.himedia.spserver.entity.Product;
//import com.himedia.spserver.repository.MemberRepository;
//import com.himedia.spserver.repository.OrderDetailReposiory;
//import com.himedia.spserver.repository.ProductRepository;
//import com.himedia.spserver.service.AdminService;
//import com.himedia.spserver.service.OrderService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//
//@SpringBootTest
//class SpServerApplicationTests {
//
//    @Autowired
//    MemberRepository mR;
//
//    @Autowired
//    AdminService as;
//
//    @Autowired
//    ProductRepository pR;
//
//    @Test
//    void contextLoads() {
//        List<Product>  mlist =pR.findByNameContaining( "부츠" );
//         System.out.println(mlist.get(0).getName());
//
//    }
//
//}
