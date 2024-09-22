package com.tarteka.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;
    
    @Getter
    @Setter
    private int year;
    
    @Getter
    @Setter
    private int votes;
    
    @Getter
    @Setter
    private double rating;

    @Column(name = "image_url")
    @Getter
    @Setter
    private String imageUrl;
}
