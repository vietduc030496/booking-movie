package com.ntu.moviecore.domain.theater.repository;

import com.ntu.moviecore.domain.theater.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
