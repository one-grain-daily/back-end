package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository {

    Donation findCurrentDonation();

    void save(Donation donation);
}
