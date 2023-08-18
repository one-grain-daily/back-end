package com.hackathon.Service;

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

    public List<Diary> Show_diaries(String username){
        User userEntity = userRepository.findByUsername(username);
        System.out.println("일기 목록 : " + userEntity.getDiaries());
        return userEntity.getDiaries();
    }
}
