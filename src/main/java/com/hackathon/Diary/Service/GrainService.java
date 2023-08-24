package com.hackathon.Diary.Service;

import com.hackathon.Diary.DTO.GrainReqDTO;
import com.hackathon.Diary.Repository.GrainRepository;
import com.hackathon.Diary.model.User;
import com.hackathon.Diary.Repository.UserRepository;
import com.hackathon.Diary.model.Grain;
import com.hackathon.donation.DonationStatus;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.donation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrainService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrainRepository grainRepository;
    @Autowired
    private DonationService donationService;
    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public void getGrain(String username){
        User userEntity = userRepository.findByUsername(username);
        Grain grain = grainRepository.findById(userEntity.getGrain().getId()).orElseThrow(()->{
            return new IllegalArgumentException(" ");
        });

        grain.setCurrent_grain_num(grain.getCurrent_grain_num() + 1);

        if(grain.getCurrent_grain_num() >= 30){
            long grainNum = grain.getCurrent_grain_num();
            grain.setDonation_grain_num(grain.getDonation_grain_num() + grain.getCurrent_grain_num());
            grain.setCurrent_grain_num(0);

            Donation nowDonation = donationRepository.findCurrentDonation();

            if(nowDonation.getUsers().contains(userEntity)){ // 유저를 이미갖고있음
                nowDonation.donate(grainNum);
            }else{
                nowDonation.donate(userEntity, grainNum);
                userEntity.getDonations().add(nowDonation);
            }
            System.out.println( "==============현재 기부량 ====================="+ nowDonation.getBasket());
            if(nowDonation.getBasket() >= 1500L){
                System.out.println("====================기부 목표 완료===========================");
                nowDonation.setStatus(DonationStatus.DONE);
                for(int i = 0; i < nowDonation.getUsers().size(); i++){
                    System.out.println(nowDonation.getUsers().get(i).getNickname());
                }
                for(int i = 0; i < userEntity.getDonations().size(); i++){
                    System.out.println("==============================");
                    System.out.println(userEntity.getDonations().get(i).getId());
                }

                donationService.createDonation();
            }

            // 트랜젝션 문제가 생긴 코드
//            donationService.donate(userEntity, grainNum);
//            if(donationService.getBasket().isMax()){
//
//            }
        }
    }

    public GrainReqDTO showGrain(String username){
        User userEntity = userRepository.findByUsername(username);
        GrainReqDTO grainReqDTO = new GrainReqDTO();
        grainReqDTO.setDonation_grain_num(userEntity.getGrain().getDonation_grain_num());
        grainReqDTO.setCurrent_grain_num(userEntity.getGrain().getCurrent_grain_num());
        return grainReqDTO;
    }
}
