package com.hackathon.donation.controller;

import com.hackathon.Repository.UserRepository;
import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.domain.Donation;
import com.hackathon.donation.service.DonationService;
import com.hackathon.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;
    private final UserRepository userRepository;

    //바구니 조회
    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket(){
        return new ResponseEntity<>(donationService.getBasket(), HttpStatus.OK);
    }

    //기부 목록 조회
    @GetMapping
    public String donationList(
            Model model
    ){

        List<Donation> donations = donationService.getDonations();
        model.addAttribute("donations", donations);
        return "donation_list";

    }

    //기부 상태 변경
    @PostMapping("/{donation_id}/done")
    public String done(
            @PathVariable Long donation_id
    ){
        donationService.done(donation_id);
        return "redirect:/donations";
    }

    @GetMapping("/create")
    public String createDonation(){
        donationService.createDonation();

        User user = new User();
        user.setUsername("조도연");

        userRepository.save(user);
        donationService.donate(user, 30L);
        return "redirect:";
    }
}
