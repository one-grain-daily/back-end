package com.hackathon.Service;

import com.hackathon.DTO.GrainReqDTO;
import com.hackathon.Repository.GrainRepository;
import com.hackathon.Repository.UserRepository;
import com.hackathon.model.Grain;
import com.hackathon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrainService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrainRepository grainRepository;

    @Transactional
    public void getGrain(String username){
        User userEntity = userRepository.findByUsername(username);
        Grain grain = grainRepository.findById(userEntity.getGrain().getId()).orElseThrow(()->{
            return new IllegalArgumentException(" ");
        });

        grain.setCurrent_grain_num(grain.getCurrent_grain_num() + 1);

        if(grain.getCurrent_grain_num() >= 30){
            grain.setDonation_grain_num(grain.getDonation_grain_num() + grain.getCurrent_grain_num());
            grain.setCurrent_grain_num(0);
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
