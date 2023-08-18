package com.hackathon.Service;

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
    }
}
