package com.himedia.spserver.controller;

import com.himedia.spserver.entity.Product;
import com.himedia.spserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService ps;

    @GetMapping("/getBestProduct")
    public HashMap<String, Object> getBestProduct() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bestProduct", ps.getBestProduct() );
        return result;
    }

    @GetMapping("/getNewProduct")
    public HashMap<String, Object> getNewProduct() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("newProduct", ps.getNewProduct() );
        return result;
    }


    @GetMapping("/categoryList")
    public HashMap<String, Object> getCategoryList(@RequestParam("category") int category) {
        HashMap<String, Object> result = new HashMap<>();
        List<Product> list = ps.getCategoryList( category );
        result.put("categoryList", list );
        return result;
    }


    @GetMapping("/getProduct")
    public HashMap<String, Object> getProduct(@RequestParam("pseq") int pseq) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("product", ps.getProduct(pseq));
        return result;
    }
}
