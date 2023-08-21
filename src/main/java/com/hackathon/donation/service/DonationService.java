package com.hackathon.donation.service;

import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;

    //기부 생성
    public void createDonation(){
        Donation donation = Donation.createDonation();
        donationRepository.save(donation);
    }

    //기부 하기
    public void donate(User user, Long grains){
        Donation donation = donationRepository.findCurrentDonation();
        donation.donate(user, grains);
    }

    //바구니 갯수 조회
    @Transactional(readOnly = true)
    public Basket getBasket(){
        Donation donation = donationRepository.findCurrentDonation();
        Basket basket = new Basket();
        basket.setCurrent_grain(donation.getBasket());
        basket.setMax_grain(10000L);
        return basket;
    }

}
