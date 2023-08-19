package com.hackathon.donation.domain;

import com.hackathon.common.entity.BaseTimeEntity;
import com.hackathon.donation.DonationStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Donation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String image_url;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

}