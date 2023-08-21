package com.hackathon.Service;

import com.hackathon.Repository.EmotionRepository;
import com.hackathon.Repository.MonthReviewRepository;
import com.hackathon.model.Emotion;
import com.hackathon.model.MonthReview;
import com.hackathon.model.User;
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
