package com.himedia.spserver.controller;

import com.himedia.spserver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cs;

    @PostMapping("/insertCart")
    public HashMap<String, Object> insertCart(
            @RequestParam("pseq") int pseq,
            @RequestParam("userid") String userid,
            @RequestParam("quantity") int quantity
    ) {
        HashMap<String, Object> result = new HashMap<>();
        cs.insertCart( pseq, userid, quantity );
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/getCartList")
    public HashMap<String, Object> getCartList( @RequestParam("userid") String userid ) {
        HashMap<String, Object> result = cs.getCartList( userid );
        return result;
    }

    @DeleteMapping("/deletecart/{cseq}")
    public HashMap<String, Object> deleteCart(@PathVariable("cseq") int cseq) {
        HashMap<String, Object> result = new HashMap<>();
        cs.deleteCart(cseq);
        result.put("msg", "ok");
        return result;
    }

}
