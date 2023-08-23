package com.hackathon.Diary.Service;

import com.hackathon.Diary.DTO.DiaryPostingDTO;
import com.hackathon.Diary.DTO.MonthReviewResDTO;
import com.hackathon.Diary.model.Diary;
import com.hackathon.Diary.model.Emotion;
import com.hackathon.Diary.model.User;
import com.hackathon.Diary.Repository.DiaryRepository;
import com.hackathon.Diary.Repository.EmotionRepository;
import com.hackathon.Diary.Repository.MonthReviewRepository;
import com.hackathon.Diary.Repository.UserRepository;
import com.hackathon.Diary.model.MonthReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EmotionRepository emotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MonthReviewRepository monthReviewRepository;

    @Autowired
    private EmotionService emotionService;
    @Autowired
    private GrainService grainService;

    @Transactional
    public void DiaryPosting(DiaryPostingDTO diaryPostingDTO, String username) {
        Diary diaryEntity = new Diary();
        diaryEntity.setContent(diaryPostingDTO.getContent());
        diaryEntity.setTitle(diaryPostingDTO.getTitle());

        Emotion emotion = new Emotion();
        emotion.setImage("image");
        emotion.setName(diaryPostingDTO.getEmotional());

        int CreteMonth = LocalDateTime.now().getMonthValue();
        emotion.setMonth(CreteMonth);

        emotionRepository.save(emotion);

        diaryEntity.setEmotion(emotion);

        User userEntity = userRepository.findByUsername(username); // 영속성 컨텍스트
        userEntity.getDiaries().add(diaryEntity);

        grainService.getGrain(username);
        diaryRepository.save(diaryEntity);
        monthReviewInsert(userEntity, emotion);
    }

    @Transactional
    public void DiaryUpdatePosting(DiaryPostingDTO diaryPostingDTO, int id, String username) {
        MonthReview monthReview = monthReviewRepository.findById(userRepository.findByUsername(username).getMonthReview().getId())
                .orElseThrow(()->{
                    return new IllegalArgumentException(" ");
                });

        Diary diaryEntity = diaryRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패!");
        });

        Emotion emotion = emotionRepository.findById(diaryEntity.getEmotion().getId()).orElseThrow(() -> {
            return new IllegalArgumentException("감정 찾기 실패");
        });


        System.out.println("===============monthReview befor Update====================");
        System.out.println(monthReview);

        monthReview.getEmotions().remove(emotion);
        diaryEntity.setTitle(diaryPostingDTO.getTitle());
        diaryEntity.setContent(diaryPostingDTO.getContent());
        emotion.setName(diaryPostingDTO.getEmotional());
        diaryEntity.setEmotion(emotion);
        monthReview.getEmotions().add(emotion);

        System.out.println("===============monthReview Update====================");
        System.out.println(monthReview);

        System.out.println("업데이트 완료!");
    }

    @Transactional
    public void DiaryDelete(int id, String username) {
        Diary diaryEntity = diaryRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패!");
        });

        MonthReview monthReview = monthReviewRepository.findById(userRepository.findByUsername(username).getMonthReview().getId())
                .orElseThrow(()->{
                    return new IllegalArgumentException(" ");
                });

        Emotion emotion = diaryEntity.getEmotion();

        monthReview.getEmotions().remove(emotion);

        User userEntity = userRepository.findByUsername(username);
        emotionService.DeleteEmotion(diaryEntity.getEmotion().getId());

        userEntity.getDiaries().remove(diaryEntity);
        diaryRepository.deleteById(id);
    }

    @Transactional
    public void monthReviewInsert(User userEntity, Emotion emotion) {
        MonthReview monthReview = monthReviewRepository.findById(userEntity.getMonthReview().getId()).orElseThrow(() -> {
            return new IllegalArgumentException(" ");
        });
        monthReview.getEmotions().add(emotion);
//        switch (month) {
//            case 1:
//                monthReview.getMonth1().add(emotion);
//                break;
//            case 2:
//                monthReview.getMonth2().add(emotion);
//                break;
//            case 3:
//                monthReview.getMonth3().add(emotion);
//                break;
//            case 4:
//                monthReview.getMonth4().add(emotion);
//                break;
//            case 5:
//                monthReview.getMonth5().add(emotion);
//                break;
//            case 6:
//                monthReview.getMonth6().add(emotion);
//                break;
//            case 7:
//                monthReview.getMonth7().add(emotion);
//                break;
//            case 8:
//                monthReview.getMonth8().add(emotion);
//                break;
//            case 9:
//                monthReview.getMonth9().add(emotion);
//                break;
//            case 10:
//                monthReview.getMonth10().add(emotion);
//                break;
//            case 11:
//                monthReview.getMonth11().add(emotion);
//                break;
//            default:
//                monthReview.getMonth12().add(emotion);
//                break;
//        }
    }

    public MonthReviewResDTO ShowMonthReview(String username, int month) {
        MonthReviewResDTO monthReviewResDTO = new MonthReviewResDTO();
        String comment;
        String nickname;
        String summery;

        User userEntity = userRepository.findByUsername(username);
        MonthReview monthReview = monthReviewRepository.findById(userEntity.getMonthReview().getId()).orElseThrow(() -> {
            return new IllegalArgumentException(" ");
        });

        nickname = userEntity.getNickname();

        int good = 0;
        int bad = 0;
        int normal = 0;

        for (int i = 0; i < monthReview.getEmotions().size(); i++) {
            if (monthReview.getEmotions().get(i).getMonth() == month) {
                if (monthReview.getEmotions().get(i).getName().equals("happy"))
                    good++;
                else if (monthReview.getEmotions().get(i).getName().equals("angry"))
                    bad++;
                else
                    normal++;
            }
        }

        if (good + bad + normal < 3) {
            comment = "일기 수가 너무 적어요.";
            summery = "빈 그릇";
            monthReviewResDTO.setComment(comment);
            monthReviewResDTO.setNickname(nickname);
            monthReviewResDTO.setSummery(summery);

            return monthReviewResDTO;
        }
        int max = good;
        if (bad > max) max = bad;
        if (normal > max) max = normal;

        if (max == good) {
            comment = (monthReviewConmment.happy);
            summery = "흰쌀밥";
        } else if (max == bad) {
            comment = (monthReviewConmment.angry);
            summery = "흑미";
        } else {
            comment = (monthReviewConmment.sad);
            summery = "잡곡밥";
        }


        monthReviewResDTO.setComment(comment);
        monthReviewResDTO.setNickname(nickname);
        monthReviewResDTO.setSummery(summery);

        return monthReviewResDTO;
    }

//    public void cntEmotion(List<Emotion> emotionList){
//        int good = 0;
//        int bad = 0;
//        int normal = 0;
//
//        for (int i = 0; i < emotionList.size(); i++){
//            if(emotionList.get(i).getName().equals("좋아요"))
//                good++;
//            else if (emotionList.get(i).getName().equals("싫어요"))
//                bad++;
//            else
//                normal++;
//        }
//        int max = good;
//        if(bad > max) max = bad;
//        if(normal > max) max = normal;
//
//        if(max == good) System.out.println(monthReviewConmment.good);
//        if(max == bad) System.out.println(monthReviewConmment.bad);
//        if(max == normal) System.out.println(monthReviewConmment.normal);
//    }
}
