package com.hackathon.Service;

import com.hackathon.DTO.UserInfoReqDTO;
import com.hackathon.Repository.UserRepository;
import com.hackathon.model.Diary;
import com.hackathon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<Diary> Show_diaries(String username){
        User userEntity = userRepository.findByUsername(username);
        System.out.println("일기 목록 : " + userEntity.getDiaries());
        return userEntity.getDiaries();
    }

    public UserInfoReqDTO userInformation(String username){
        User userEntity = userRepository.findByUsername(username);
        UserInfoReqDTO userInfoReqDTO = new UserInfoReqDTO();
        userInfoReqDTO.setNickname(userEntity.getNickname());
        userInfoReqDTO.setUsername(userEntity.getUsername());
        userInfoReqDTO.setCurrent_grain_num(userEntity.getGrain().getCurrent_grain_num());
        userInfoReqDTO.setDonation_grain_num(userEntity.getGrain().getDonation_grain_num());

        return userInfoReqDTO;
    }
}
