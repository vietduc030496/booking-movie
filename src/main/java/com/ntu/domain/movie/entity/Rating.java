package com.ntu.domain.movie.entity;

import com.ntu.domain.authentication.entity.User;
import com.ntu.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_rating")
@Getter
@Setter
public class Rating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private float score;

    private String comment;
}
