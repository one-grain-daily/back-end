package com.hackathon.Diary.DTO;

import lombok.Data;

@Data
public class UserInfoReqDTO {
    String username;
    String nickname;
    int current_grain_num;
    int donation_grain_num;
}
