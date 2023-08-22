package com.hackathon.donation.repository;

import com.hackathon.donation.domain.Donation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Override
    public List<Donation> findAll() {
        String sql = "select d from Donation d";
        return em.createQuery(sql).getResultList();
    }

    @Override
    public Donation findById(Long id) {
        return em.find(Donation.class, id);
    }
}
