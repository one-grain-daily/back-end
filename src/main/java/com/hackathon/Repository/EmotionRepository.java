package com.hackathon.Repository;

import com.hackathon.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Integer> {
}
