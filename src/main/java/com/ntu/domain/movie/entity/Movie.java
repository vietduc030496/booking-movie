package com.ntu.domain.movie.entity;

import com.ntu.domain.base.entity.BaseEntity;
import com.ntu.domain.movie.entity.converter.AgeRatingConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_movie")
@Getter
@Setter
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "running_time")
    private int runningTime;

    private String genre;

    @Convert(converter = AgeRatingConverter.class)
    @Column(name = "age_rating", nullable = false)
    private AgeRating ageRating;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "release_date")
    private LocalDate releaseDate;

}
