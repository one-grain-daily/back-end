package com.hackathon.Controller;

import com.hackathon.DTO.DiaryPostingDTO;
import com.hackathon.Service.DiaryService;
import com.hackathon.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    @GetMapping("api/v1/user/getDiary")
    public String showDiary(Authentication authentication){
        userService.Show_diaries(authentication.getName());
        return "일기 목록 가져오기 완료";
    }

    @PostMapping("/api/v1/user/diaryPosting")
    public String diaryPosting(@RequestBody DiaryPostingDTO diarypostingDTO, Authentication authentication){
        diaryService.DiaryPosting(diarypostingDTO, authentication.getName());

        return "<h1>다이어리 작성 완료, 쌀 1개 획득</h1>";
    }

    @PutMapping("api/v1/user/updatePosting/{id}")
    public String diaryUpdate(@RequestBody DiaryPostingDTO diaryPostingDTO, @PathVariable int id){
        diaryService.DiaryUpdatePosting(diaryPostingDTO, id);
        return "다이어리 업데이트 완료!";
    }

    @DeleteMapping("api/v1/user/deletePosting/{id}")
    public String diaryDelete(@PathVariable int id, Authentication authentication){
        diaryService.DiaryDelete(id, authentication.getName());
        return "일기 삭제 완료!";
    }
}
