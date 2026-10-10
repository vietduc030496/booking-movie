package com.ntu.persistence.theater.repository;

import com.ntu.persistence.theater.entity.RoomTypeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
