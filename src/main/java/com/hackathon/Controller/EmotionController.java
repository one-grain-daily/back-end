package com.hackathon.Controller;

import com.hackathon.Repository.EmotionRepository;
import com.hackathon.model.Emotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/emotions")
@RequiredArgsConstructor
@Slf4j
public class EmotionController {

    private final EmotionRepository emotionRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    //감정과 한달 한 숱 추가
    @GetMapping("/add")
    public String addEmotion(){

        return "emotion_add";
    }

    @PostMapping("/add")
    public String addEmotion(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam("message") String message
    ) {
        System.out.println("EmotionController.addEmotion");
        try {
            // 파일 업로드 처리
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("이미지 경로 = {}", uploadPath.toAbsolutePath());

            // 감정 정보 저장
            Emotion emotion = new Emotion();
            emotion.setName(name);
            emotion.setImage("images/" + fileName); // 실제 업로드된 파일의 URL로 수정

            emotionRepository.save(emotion);

            return "redirect:/emotions";  // 감정 추가 성공 페이지로 리다이렉트

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error-page";  // 에러 페이지로 리다이렉트
        }
    }

    @GetMapping
    public String getEmotions(Model model){

        List<Emotion> emotions = emotionRepository.findAll();
        model.addAttribute("emotions", emotions);
        return "emotion_list";
    }

}
