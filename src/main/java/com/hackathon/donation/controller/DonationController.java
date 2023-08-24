package com.hackathon.donation.controller;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.hackathon.Diary.Repository.UserRepository;
import com.hackathon.common.util.UploadObjectFromMemory;
import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.repository.DonationRepository;
import com.hackathon.donation.service.DonationService;
import com.hackathon.Diary.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;
    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final Storage storage;

    //바구니 조회
    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket() {
        System.out.println(donationService.getBasket().getCurrent_grain());
        return new ResponseEntity<>(donationService.getBasket(), HttpStatus.OK);
    }

    //기부 목록 조회
    @GetMapping
    public String donationList(
            Model model
    ) {

        List<Donation> donations = donationService.getDonations();
        model.addAttribute("donations", donations);
        return "donation_list";

    }

    @GetMapping("/{donation_id}")
    public String donateList(

            Model model,
            @PathVariable Long donation_id

    ){
        Donation donation = donationRepository.findById(donation_id);
        model.addAttribute("donation", donation);
        return "donation";
    }

    @PostMapping("/{donation_id}/uploadImg")
    public String uploadImg(

            @PathVariable Long donation_id,
            @RequestParam MultipartFile imageFile

    ) throws IOException {

        /**
         * 이미지 업로드
         */
        String bucketName = "one-grain-daily";
        String fileName = imageFile.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        byte[] bytes = imageFile.getBytes();
        storage.create(blobInfo, bytes);

        donationService.setImage(donation_id, "https://storage.googleapis.com/"+bucketName+"/"+fileName);

        return "redirect:";

    }

    //기부 상태 변경
    @PostMapping("/{donation_id}/done")
    public String done(
            @PathVariable Long donation_id
    ) {
        donationService.done(donation_id);
        return "redirect:/donations";
    }

    @GetMapping("/create")
    public String createDonation() {
        donationService.createDonation();

//        User user = new User();
//        user.setUsername("조도연");
//
//        userRepository.save(user);
//        donationService.donate(user, 30L);
        return "redirect:";
    }
}
