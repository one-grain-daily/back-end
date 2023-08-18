package com.hackathon.Repository;

import com.hackathon.model.Grain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrainRepository extends JpaRepository<Grain, Integer> {
}
