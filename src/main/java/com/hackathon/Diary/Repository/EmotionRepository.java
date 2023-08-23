package com.hackathon.Diary.Repository;

import com.hackathon.Diary.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Integer> {
}
