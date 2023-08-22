package com.hackathon.model;

import com.hackathon.donation.domain.Donation;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String nickname;
    @OneToOne
    private Grain grain;
    @OneToOne
    private MonthReview monthReview;
    private String roles;
    @OneToMany
    private List<Diary> diaries = new ArrayList<>();
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(",")); // 롤이 한개가 아님
        }
        return new ArrayList<>();
    }

    @ManyToMany
    private List<Donation> donations;

}
