package com.hackathon.Controller;

import com.hackathon.Repository.GrainRepository;
import com.hackathon.Repository.UserRepository;
import com.hackathon.model.Grain;
import com.hackathon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public GrainRepository grainRepository;

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody User user){
        user.setRoles("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        String email = user.getEmail();
        user.setPassword(encPassword);
        user.setEmail(email);

        Grain grain = new Grain();
        grain.setCurrent_grain_num(0);
        grain.setDonation_grain_num(0);
        grainRepository.save(grain);
        user.setGrain(grain);

        userRepository.save(user);
        return "회원가입 완료";
    }

    @GetMapping("/api/v1/user/u")
    public String user(Authentication authentication){
        System.out.println("로그인된 아이디 : " + authentication.getName());
        return "user";
    }
    @GetMapping("/api/v1/manager")
    public String manager(){
        return "manager";
    }
    @GetMapping("/api/v1/admin")
    public String admin(){
        return "admin";
    }
}
