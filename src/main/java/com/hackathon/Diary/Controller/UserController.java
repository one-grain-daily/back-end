package com.hackathon.Diary.Controller;

import com.hackathon.Diary.DTO.GrainReqDTO;
import com.hackathon.Diary.DTO.UserInfoReqDTO;
import com.hackathon.Diary.Service.GrainService;
import com.hackathon.Diary.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private GrainService grainService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/user/grainStatus")
    public GrainReqDTO grainStatus(Authentication authentication){
        GrainReqDTO grainReqDTO = grainService.showGrain(authentication.getName());
        System.out.println("==============================쌀 현황 ===========================");
        System.out.println(grainReqDTO);
        return grainReqDTO;
    }

    @GetMapping("/api/v1/user/getUserInfo")
    public UserInfoReqDTO getUserInfo(Authentication authentication){
        UserInfoReqDTO userInfoReqDTO = userService.userInformation(authentication.getName());
        System.out.println("===============================유저 정보 ======================");
        System.out.println(userInfoReqDTO);
        return userInfoReqDTO;
    }
}
