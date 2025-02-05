package com.himedia.spserver.controller;

import com.himedia.spserver.entity.Member;
import com.himedia.spserver.service.MailServics;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MailController {

    private final MailServics ms;
    private int number;

    @PostMapping("/sendMail")
    public HashMap<String, Object> sendMail(@RequestParam("email") String email ) {
        HashMap<String, Object> result = new HashMap<>();
        number = ms.sendMail( email );
        result.put("msg", "ok");
        return result;
    }

    @PostMapping("/codecheck")
    public HashMap<String, Object> codecheck(@RequestParam("usercode") String usercode ) {
        HashMap<String, Object> result = new HashMap<>();
        String num = String.valueOf(number);
        if( num.equals(usercode) )
            result.put("msg", "ok");
        else
            result.put("msg", "not_ok");

        return result;
    }



}
