package com.hackathon.Diary.Repository;

import com.hackathon.Diary.model.Grain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrainRepository extends JpaRepository<Grain, Integer> {
}
