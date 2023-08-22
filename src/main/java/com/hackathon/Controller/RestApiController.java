package com.hackathon.Controller;

import com.hackathon.Repository.GrainRepository;
import com.hackathon.Repository.MonthReviewRepository;
import com.hackathon.Repository.UserRepository;
import com.hackathon.Service.EmotionService;
import com.hackathon.model.Grain;
import com.hackathon.model.MonthReview;
import com.hackathon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public GrainRepository grainRepository;
    @Autowired
    public MonthReviewRepository monthReviewRepository;

    @Autowired
    public EmotionService emotionService;

    @PostMapping("/join")
    public String join(@RequestBody User user){
        user.setRoles("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        String nickname = user.getNickname();
        user.setPassword(encPassword);
        user.setNickname(nickname);

        Grain grain = new Grain();
        grain.setCurrent_grain_num(0);
        grain.setDonation_grain_num(0);

        MonthReview monthReview = new MonthReview();

        grainRepository.save(grain);
        user.setGrain(grain);

        monthReviewRepository.save(monthReview);
        user.setMonthReview(monthReview);

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

    @DeleteMapping("/deleteEmotion/{id}")
    public void de(@PathVariable int id){
        emotionService.DeleteEmotion(id);
    }
}
