package com.hackathon.Service;

import com.hackathon.Repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmotionService {
    @Autowired
    private EmotionRepository emotionRepository;

    @Transactional
    public void DeleteEmotion(int id){
        emotionRepository.deleteById(id);
    }
}
