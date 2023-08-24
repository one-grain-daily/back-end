package com.hackathon.donation.service;

import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.Diary.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public void donate(User user, Long grains){
        Donation donation = donationRepository.findCurrentDonation();
        if(donation.getUsers().contains(user)) { // 포함이면
            //donation.donate(grains);
            donation.setBasket(donation.getBasket() + grains);
        }
        else {
            donation.getUsers().add(user);
            donation.setBasket(donation.getBasket() + grains);
        }
    }

    //바구니 갯수 조회
    @Transactional(readOnly = true)
    public Basket getBasket(){
        Donation donation = donationRepository.findCurrentDonation();
        Basket basket = new Basket();
        basket.setCurrent_grain(donation.getBasket());
        basket.setMax_grain(1500L);
        return basket;
    }

    public List<Donation> getDonations(){
        return donationRepository.findAll();
    }

    public void done(Long donation_id){
        Donation donation = donationRepository.findById(donation_id);
        donation.done();
    }

    public void setImage(Long donation_id, String imageUrl){
        Donation donation = donationRepository.findById(donation_id);
        donation.setImage_url(imageUrl);
    }

}
