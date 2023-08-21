package com.hackathon.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    //회원가입 뷰
    @GetMapping("/join")
    public String join(){
       return "join";
    }

    //로그인 뷰
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
