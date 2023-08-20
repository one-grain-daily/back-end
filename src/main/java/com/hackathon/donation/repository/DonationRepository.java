package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select max(d.donation_id) from Donation d" )
    Optional<Donation> findLastDonation();

}
