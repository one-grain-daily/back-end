package com.hackathon.donation.service;

import com.hackathon.Repository.GrainRepository;
import com.hackathon.Repository.UserRepository;
import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.model.Grain;
import com.hackathon.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class DonationServiceTest {

    @Autowired private DonationService donationService;
    @Autowired private DonationRepository donationRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private GrainRepository grainRepository;

    @Test
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
        user.setEmail("test@test");
        user.setRoles("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = rawPassword;
        String email = user.getEmail();
        user.setPassword(encPassword);
        user.setEmail(email);

        Grain grain = new Grain();
        grain.setCurrent_grain_num(0);
        grain.setDonation_grain_num(0);
        grainRepository.save(grain);
        user.setGrain(grain);

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



        //when

        //then
    }




    @Test
    public void getBasket() throws Exception {
        //given

        //when

        //then
    }


}