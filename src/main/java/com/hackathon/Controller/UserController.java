package com.hackathon.Controller;

import com.hackathon.DTO.GrainReqDTO;
import com.hackathon.Service.GrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private GrainService grainService;

    @GetMapping("/api/v1/user/grainStatus")
    public GrainReqDTO grainStatus(Authentication authentication){
        GrainReqDTO grainReqDTO = grainService.showGrain(authentication.getName());
        System.out.println("==============================쌀 현황 ===========================");
        System.out.println(grainReqDTO);
        return grainReqDTO;
    }
}
