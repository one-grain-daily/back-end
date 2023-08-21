package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository {

    Donation findCurrentDonation();

    void save(Donation donation);

    List<Donation> findAll();

}
