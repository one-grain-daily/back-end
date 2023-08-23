package com.hackathon.Diary.Repository;

import com.hackathon.Diary.model.MonthReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthReviewRepository extends JpaRepository<MonthReview, Integer> {
}
