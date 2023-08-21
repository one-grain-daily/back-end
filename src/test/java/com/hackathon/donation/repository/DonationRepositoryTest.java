package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class DonationRepositoryTest {

    @Autowired private DonationRepository donationRepository;

    @Test
    @DisplayName("findCurrentDonation 은 가장 최근 기부를 가져 온다.")
    void findCurrentDonation() {

        Donation donation1 = Donation.createDonation();
        Donation donation2 = Donation.createDonation();
        donationRepository.save(donation1);
        donationRepository.save(donation2);

        Donation currentDonation = donationRepository.findCurrentDonation();

        assertThat(donation2).isEqualTo(currentDonation);

    }

    @Test
    void save() {

        Donation donation = Donation.createDonation();
        donationRepository.save(donation);

        Donation savedDonation = donationRepository.findCurrentDonation();

        assertThat(donation).isEqualTo(savedDonation);
    }
}