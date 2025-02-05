package com.himedia.spserver.controller;

import com.himedia.spserver.dto.OrderDetailDto;
import com.himedia.spserver.entity.Order_Detail;
import com.himedia.spserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService os;


    @PostMapping("/insertOrders")
    public HashMap<String, Object> insertOrders(@RequestParam("userid") String userid ) {
        HashMap<String, Object> result = new HashMap<>();
        int oseq = os.insertOrders(userid);
        System.out.println("oseq : " + oseq);
        result.put("oseq", oseq);
        return result;
    }


    @PostMapping("/insertOrderDetail")
    public HashMap<String, Object> insertOrderDetail(@RequestParam("oseq") int oseq, @RequestParam("cseq") int cseq) {
        HashMap<String, Object> result = new HashMap<>();
        os.insertOrderDetail(oseq, cseq);
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/getOrderList")
    public HashMap<String, Object> getOrderList(@RequestParam("oseq") int oseq) {
        HashMap<String, Object> result = os.getOrderList(oseq);
        return result;
    }


    @PostMapping("/insertOrderDetailOne")
    public HashMap<String, Object> insertOrderDetailOne( @RequestParam("oseq") int oseq, @RequestParam("pseq") int pseq, @RequestParam("quantity") int quantity) {
        HashMap<String, Object> result = new HashMap<>();
        os.insertOrderDetailOne( oseq, pseq, quantity);
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/getOrderIng")
    public HashMap<String, Object> getOrderIng(@RequestParam("userid") String userid) {
        HashMap<String, Object> result = new HashMap<>();
        List<OrderDetailDto> list = os.getOrderIng(userid);
        result.put("orderList", list);
        return result;
    }


    @GetMapping("/getOrderAll")
    public HashMap<String, Object> getOrderAll(@RequestParam("userid") String userid) {
        HashMap<String, Object> result = new HashMap<>();
        List<OrderDetailDto> list = os.getOrderAll(userid);
        result.put("orderList", list);
        return result;

    }


}
