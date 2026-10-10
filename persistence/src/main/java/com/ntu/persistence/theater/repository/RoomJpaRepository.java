package com.ntu.persistence.theater.repository;

import com.ntu.persistence.theater.entity.RoomJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r " +
            "JOIN FETCH r.theater t " +
            "LEFT JOIN FETCH r.roomType " +
            "WHERE (:theaterId IS NULL OR t.id = :theaterId) " +
            "ORDER BY t.name, r.name")
    List<Room> findAdminRooms(@Param("theaterId") Long theaterId);

    /**
     * Locks the room row so that concurrent showtime creations for the same room are serialized.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
    Optional<Room> findByIdForUpdate(@Param("roomId") long roomId);

    boolean existsByTheaterIdAndNameIgnoreCase(Long theaterId, String name);

    boolean existsByTheaterIdAndNameIgnoreCaseAndIdNot(Long theaterId, String name, Long id);
}
