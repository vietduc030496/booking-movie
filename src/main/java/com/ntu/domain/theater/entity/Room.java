package com.ntu.domain.theater.entity;

import com.ntu.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_room")
@Getter
@Setter
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    private int capacity;
}
