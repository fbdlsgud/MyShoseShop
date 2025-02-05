package com.himedia.spserver.controller;

import com.himedia.spserver.entity.Qna;
import com.himedia.spserver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService cs;

    @GetMapping("/qnaList")
    public HashMap<String, Object> qnaList(@RequestParam("page") int page){
        HashMap<String, Object> result = cs.getQnaList(page);
        return result;
    }


    @PostMapping("/confirmPass")
    public HashMap<String, Object> confirmPass(@RequestParam("qseq") int qseq, @RequestParam("pass") String pass) {
        HashMap<String, Object> result = new HashMap<>();
        Qna qna = cs.getQna(qseq);
        if( qna.getPass().equals(pass) )   result.put("msg", "ok");
        else   result.put("msg", "fail");
        return result;
    }
    @GetMapping("/getQna")
    public HashMap<String, Object> getQna(@RequestParam("qseq") int qseq){
        System.out.println("1");
        HashMap<String, Object> result = new HashMap<>();
        Qna qna = cs.getQna(qseq);
        System.out.println(qna);
        result.put("qna", qna);
        return result;
    }

    @PostMapping("writeQna")
    public HashMap<String, Object> writeQna( @RequestBody Qna qna){
        HashMap<String, Object> result = new HashMap<>();
        cs.insertQna( qna );
        result.put("msg", "ok");
        return result;
    }

}
