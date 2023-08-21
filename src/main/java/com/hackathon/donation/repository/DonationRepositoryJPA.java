package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DonationRepositoryJPA implements DonationRepository{

    private final EntityManager em;

    @Override
    public Donation findCurrentDonation() {
        String sql = "select d from Donation d order by d.id desc";
        return (Donation) em.createQuery(sql).getResultList().get(0);

    }

    public void save(Donation donation){
        em.persist(donation);
    }

}
