package com.hackathon.Repository;

import com.hackathon.model.MonthReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthReviewRepository extends JpaRepository<MonthReview, Integer> {
}
