package com.hackathon.Service;

import com.hackathon.DTO.DiaryPostingDTO;
import com.hackathon.Repository.DiaryRepository;
import com.hackathon.Repository.EmotionRepository;
import com.hackathon.Repository.UserRepository;
import com.hackathon.model.Diary;
import com.hackathon.model.Emotion;
import com.hackathon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EmotionRepository emotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmotionService emotionService;
    @Autowired
    private GrainService grainService;

    @Transactional
    public void DiaryPosting(DiaryPostingDTO diaryPostingDTO, String username){
        Diary diaryEntity = new Diary();
        diaryEntity.setContent(diaryPostingDTO.getContent());
        diaryEntity.setTitle(diaryPostingDTO.getTitle());

        Emotion emotion = new Emotion();
        emotion.setImage("image");
        emotion.setName(diaryPostingDTO.getEmotional());
        emotionRepository.save(emotion);

        diaryEntity.setEmotion(emotion);

        User userEntity = userRepository.findByUsername(username); // 영속성 컨텍스트
        userEntity.getDiaries().add(diaryEntity);

        grainService.getGrain(username);
        diaryRepository.save(diaryEntity);
        System.out.println(userEntity);
    }

    @Transactional
    public void DiaryUpdatePosting (DiaryPostingDTO diaryPostingDTO, int id){
        Diary diaryEntity = diaryRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패!");
        });

        Emotion emotion = emotionRepository.findById(diaryEntity.getEmotion().getId()).orElseThrow(()->{
            return new IllegalArgumentException("감정 찾기 실패");
        });

        diaryEntity.setTitle(diaryPostingDTO.getTitle());
        diaryEntity.setContent(diaryPostingDTO.getContent());
        emotion.setName(diaryPostingDTO.getEmotional());
        diaryEntity.setEmotion(emotion);
        System.out.println(diaryEntity);
        System.out.println("업데이트 완료!");
    }

    @Transactional
    public void DiaryDelete(int id, String username){
        Diary diaryEntity = diaryRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패!");
        });

        User userEntity = userRepository.findByUsername(username);
        emotionService.DeleteEmotion(diaryEntity.getEmotion().getId());
        userEntity.getDiaries().remove(diaryEntity);
        diaryRepository.deleteById(id);
    }
}
