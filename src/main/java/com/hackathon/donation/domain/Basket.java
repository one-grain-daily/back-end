package com.hackathon.donation.domain;


import lombok.Data;

@Data
public class Basket {

    private Long max_grain;
    private Long current_grain;

    /**
     * 비즈니스 로직
     */

    public boolean isMax(){
        return max_grain < current_grain;
    }

}
