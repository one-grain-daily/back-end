package com.hackathon.donation.controller;

import com.hackathon.donation.domain.Basket;
import com.hackathon.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donation")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket(){
        return new ResponseEntity<>(donationService.getBasket(), HttpStatus.OK);
    }

}
