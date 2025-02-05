package com.himedia.spserver.controller;

import com.himedia.spserver.entity.Order_Detail;
import com.himedia.spserver.entity.Product;
import com.himedia.spserver.service.AdminService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    AdminService as;

    @GetMapping("/getProductList")
    public HashMap<String,Object> getProductList(
            @RequestParam("page") int page,
            @RequestParam(value="key", required = false) String key ){
        if( key == null) key="";
        HashMap<String,Object> result = as.getPrductList(page, key);
        return result;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileup")
    public HashMap<String,Object> fileUp( @RequestParam("image") MultipartFile file ){
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/product_images");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String f1 = filename.substring(0, filename.indexOf("."));
        String f2 = filename.substring( filename.lastIndexOf(".") );
        String uploadPath = path + "/" + f1 + dt + f2;
        try {
            file.transferTo(new File(uploadPath));
            result.put("savefilename", f1 + dt + f2);
            result.put("image", filename );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @PostMapping("/insertProduct")
    public HashMap<String,Object> insertProduct(@RequestBody Product product ){
        HashMap<String, Object> result = new HashMap<>();
        as.insertProduct( product );
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/getProduct")
    public HashMap<String,Object> getProduct( @RequestParam("pseq") int pseq ){
        HashMap<String, Object> result = new HashMap<>();
        Product p = as.getProduct(pseq);
        result.put("product", p);
        return result;
    }

    @DeleteMapping("/deleteProduct/{pseq}")
    public HashMap<String,Object> deleteProduct(@PathVariable("pseq") int pseq ){
        HashMap<String, Object> result = new HashMap<>();
        as.deleteProduct(pseq);
        result.put("msg", "ok");
        return result;
    }


    @PostMapping("/updateProduct")
    public HashMap<String,Object> updateProduct(@RequestBody Product product ){
        HashMap<String, Object> result = new HashMap<>();
        as.updateProduct( product);
        result.put("msg", "ok");
        return result;
    }



    @GetMapping("/getOrderList")
    public HashMap<String,Object> getOrderList(
            @RequestParam("page") int page,
            @RequestParam(value="key", required = false) String key
    ){
        if( key == null) key="";
        HashMap<String, Object> result = as.getOrderList(page, key);
        return result;
    }


    @GetMapping("/getMemberList")
    public HashMap<String,Object> getMemberList(
            @RequestParam("page") int page,
            @RequestParam(value="key", required = false) String key
    ){
        if( key == null) key="";
        HashMap<String, Object> result = as.getMemberList(page, key);
        //System.out.println("Order controller key list size : ", result.get("orderList").size());
        return result;
    }


    @PostMapping("/nextResult")
    public HashMap<String,Object> nextResult( @RequestParam("odseq") int odseq ){
        HashMap<String, Object> result = new HashMap<>();
        as.nextResult( odseq );
        result.put("msg", "ok");
        return result;
    }


    @PostMapping("/changeRoleAdmin")
    public HashMap<String,Object> changeRoleAdmin( @RequestParam("userid") String userid ){
        System.out.println("userid : " + userid);
        HashMap<String, Object> result = new HashMap<>();
        as.changeRoleAdmin(userid);
        result.put("msg", "ok");
        return result;
    }

    @PostMapping("/changeRoleUser")
    public HashMap<String,Object> changeRoleUser( @RequestParam("userid") String userid ){
        System.out.println("userid : " + userid);
        HashMap<String, Object> result = new HashMap<>();
        as.changeRoleUser(userid);
        result.put("msg", "ok");
        return result;
    }



    @GetMapping("/getQnaList")
    public HashMap<String,Object> getQnaList(
            @RequestParam("page") int page,
            @RequestParam(value="key", required = false) String key
    ){
        if( key == null) key="";
        HashMap<String, Object> result = as.getQnaList(page, key);
        return result;
    }



    @GetMapping("/getQna")
    public HashMap<String,Object> getQna(  @RequestParam("qseq") int qseq ){
        HashMap<String, Object> result = new HashMap<>();
        result.put("qna", as.getQna(qseq ));
        return result;
    }


    @PostMapping("/writeReply")
    public HashMap<String,Object> writeReply( @RequestParam("reply") String reply, @RequestParam("qseq") int qseq ){
        HashMap<String, Object> result = new HashMap<>();
        as.updateReply( reply, qseq );
        result.put("msg", "ok");
        return result;
    }
}
