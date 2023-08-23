package com.hackathon.Diary.Service;

import com.hackathon.Diary.Repository.EmotionRepository;
import com.hackathon.Diary.Repository.MonthReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmotionService {
    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private MonthReviewRepository monthReviewRepository;

    @Transactional
    public void DeleteEmotion(int id){
        emotionRepository.deleteById(id);
    }
}
