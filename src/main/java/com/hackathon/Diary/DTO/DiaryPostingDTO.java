package com.hackathon.Diary.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DiaryPostingDTO {
    String title;
    String content;
    String emotional;
}
