package com.ntu.moviecore.domain.movie.repository;

import com.ntu.moviecore.domain.movie.entity.AgeRating;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.theater.entity.Room;
import com.ntu.moviecore.domain.theater.entity.Theater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ShowtimeRepositoryTest {

    /**
     * Existing showtime of the room: 18:00 - 20:00.
     */
    private static final LocalDateTime EXISTING_START = LocalDateTime.of(2025, 9, 7, 18, 0);
    private static final LocalDateTime EXISTING_END = EXISTING_START.plusHours(2);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    private Room room;

    private Room otherRoom;

    @BeforeEach
    void setUp() {
        Movie movie = new Movie();
        movie.setTitle("Movie");
        movie.setAgeRating(AgeRating.P);
        movie.setRunningTime(120);
        entityManager.persist(movie);

        Theater theater = new Theater();
        theater.setName("Theater");
        entityManager.persist(theater);

        room = persistRoom(theater, "Room 1");
        otherRoom = persistRoom(theater, "Room 2");

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setRoom(room);
        showtime.setStartTime(EXISTING_START);
        showtime.setEndTime(EXISTING_END);
        entityManager.persistAndFlush(showtime);
    }

    @ParameterizedTest(name = "{0} - {1} overlaps: {2}")
    @CsvSource({
            "2025-09-07T18:00, 2025-09-07T20:00, true",   // same slot
            "2025-09-07T17:00, 2025-09-07T18:30, true",   // ends inside
            "2025-09-07T19:00, 2025-09-07T21:00, true",   // starts inside
            "2025-09-07T18:30, 2025-09-07T19:30, true",   // contained
            "2025-09-07T17:00, 2025-09-07T21:00, true",   // contains
            "2025-09-07T16:00, 2025-09-07T18:00, false",  // ends when existing starts
            "2025-09-07T20:00, 2025-09-07T22:00, false",  // starts when existing ends
            "2025-09-07T21:00, 2025-09-07T23:00, false"   // later
    })
    void exists_overlapping_showtime_in_same_room(LocalDateTime start, LocalDateTime end, boolean expected) {
        assertEquals(expected, showtimeRepository.existsOverlappingShowtime(room.getId(), start, end));
    }

    @Test
    void same_slot_in_another_room_does_not_overlap() {
        assertFalse(showtimeRepository.existsOverlappingShowtime(otherRoom.getId(), EXISTING_START, EXISTING_END));
    }

    private Room persistRoom(Theater theater, String name) {
        Room newRoom = new Room();
        newRoom.setName(name);
        newRoom.setTheater(theater);
        newRoom.setCapacity(50);
        return entityManager.persist(newRoom);
    }
}
