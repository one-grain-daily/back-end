package com.hackathon.Diary.Controller;

import com.hackathon.Diary.DTO.DiaryPostingDTO;
import com.hackathon.Diary.DTO.MonthReviewResDTO;
import com.hackathon.Diary.model.Diary;
import com.hackathon.Diary.model.User;
import com.hackathon.Diary.Service.DiaryService;
import com.hackathon.Diary.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    @GetMapping("api/v1/user/getDiary/{date}") // 2023-01-01-0000 이라가정
    public Diary getDiary(@PathVariable String date, Authentication authentication){
        User userEntity = userService.findUser(authentication.getName());
        Diary diary = new Diary();

        String[] times = date.split(" ");
        String year = times[0].substring(0, times[0].length() - 1);
        String month = times[1].substring(0, times[1].length() - 1);
        String day = times[2].substring(0, times[2].length() - 1);

        System.out.println(year + " " + month + " " + day);

        for(int i = 0; i < userEntity.getDiaries().size(); i++){
            Diary tmp_diary = userEntity.getDiaries().get(i);

            LocalDateTime daytime = tmp_diary.getCreateTime().toLocalDateTime();
            String tmp_year = String.valueOf(daytime.getYear());
            String tmp_month = String.valueOf(daytime.getMonthValue());
            String tmp_day = String.valueOf(daytime.getDayOfMonth());

            if((year.equals(tmp_year) && month.equals(tmp_month)) && day.equals(tmp_day)){
                diary = tmp_diary;
                break;
            }
        }
        return diary;
    }

    @GetMapping("/api/v1/user/getDiary")
    public String showDiary(Authentication authentication){
        userService.Show_diaries(authentication.getName());
        return "일기 목록 가져오기 완료";
    }

    @PostMapping("/api/v1/user/diaryPosting")
    public String diaryPosting(@RequestBody DiaryPostingDTO diarypostingDTO, Authentication authentication){
        diaryService.DiaryPosting(diarypostingDTO, authentication.getName());

        return "다이어리 작성 완료, 쌀 1개 획득!!";
    }

    @PutMapping("api/v1/user/updatePosting/{id}")
    public String diaryUpdate(@RequestBody DiaryPostingDTO diaryPostingDTO, @PathVariable int id, Authentication authentication){
        diaryService.DiaryUpdatePosting(diaryPostingDTO, id, authentication.getName());
        return "다이어리 업데이트 완료!";
    }

    @DeleteMapping("api/v1/user/deletePosting/{id}")
    public String diaryDelete(@PathVariable int id, Authentication authentication){
        diaryService.DiaryDelete(id, authentication.getName());
        return "일기 삭제 완료!";
    }

    @GetMapping("api/v1/user/monthreview/{month}")
    public MonthReviewResDTO monthReview (Authentication authentication , @PathVariable int month){
        MonthReviewResDTO monthReviewResDTO = diaryService.ShowMonthReview(authentication.getName(), month);
        return monthReviewResDTO;
    }
}
