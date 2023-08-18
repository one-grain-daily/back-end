package com.hackathon.model;

import antlr.collections.List;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class MonthReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
