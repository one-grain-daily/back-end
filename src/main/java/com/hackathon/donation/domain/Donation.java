package com.hackathon.donation.domain;

import com.hackathon.common.entity.BaseTimeEntity;
import com.hackathon.donation.DonationStatus;
import com.hackathon.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Donation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long donation_id;
    private String image_url;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    private Long basket = 0L;

    public static Donation createDonation(){
        Donation donation = new Donation();
        donation.setStatus(DonationStatus.PROGRESS);
        donation.setImage_url(null);
        return donation;
    }

    public void donate(User user, Long basket){
        users.add(user);
        this.basket += basket;
    }

}