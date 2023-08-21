package com.hackathon.donation.service;

import com.hackathon.Repository.UserRepository;
import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class DonationServiceTest {

    @Autowired private DonationService donationService;
    @Autowired private DonationRepository donationRepository;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("기부 생성")
    public void createDonation() throws Exception {
        //given

        //when
        donationService.createDonation();

        //then
        assertNotNull(donationRepository.findCurrentDonation());
    }


    @Test
    @DisplayName("기부를 하면 쌀이 늘어난다.")
    public void donate_grain() throws Exception {
        //given
        donationService.createDonation();
        Donation donation = donationRepository.findCurrentDonation();
        User user = new User();
        userRepository.save(user);

        Long defaultGrain = donation.getBasket();

        //when
        donationService.donate(user, 100L);
        Basket basket = donationService.getBasket();

        //then
        //System.out.println("defaultGrain = " + defaultGrain);
        assertEquals(defaultGrain, basket.getCurrent_grain()-100L);

    }

    @Test
    @DisplayName("기부를 하면 사용자가 추가된다.")
    public void donate_user() throws Exception {
        //given
        donationService.createDonation();
        Donation donation = donationRepository.findCurrentDonation();
        User user = new User();
        userRepository.save(user);

        Long defaultGrain = donation.getBasket();

        //when
        donationService.donate(user, 100L);
        Basket basket = donationService.getBasket();

        //then
        //사용자 수
        assertEquals(donation.getUsers().size(), 1);
        //사용자
        assertEquals(donation.getUsers().get(0), user);
    }


}