package com.ntu.adminservice.application.theater.service;

import com.ntu.common.exception.BusinessException;
import com.ntu.moviecore.application.theater.dto.RoomNewRequest;
import com.ntu.persistence.theater.entity.RoomJpaEntity;
import com.ntu.persistence.theater.entity.TheaterJpaEntity;
import com.ntu.persistence.theater.repository.RoomJpaRepository;
import com.ntu.persistence.theater.repository.RoomTypeJpaRepository;
import com.ntu.persistence.theater.repository.TheaterJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.ntu.common.constant.AdminMessageKeyConstant.ROOM_NAME_DUPLICATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomAdminServiceTest {

    private static final long THEATER_ID = 1L;
    private static final long ROOM_ID = 2L;
    private static final String ROOM_NAME = "Room 1";

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private RoomAdminService roomAdminService;

    @Test
    void add_new_room_success() {
        Theater theater = new Theater();
        when(roomRepository.existsByTheaterIdAndNameIgnoreCase(THEATER_ID, ROOM_NAME)).thenReturn(false);
        when(theaterRepository.findById(THEATER_ID)).thenReturn(Optional.of(theater));

        roomAdminService.addNewRoom(newRequest());

        ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(captor.capture());
        Room saved = captor.getValue();
        assertEquals(ROOM_NAME, saved.getName());
        assertEquals(40, saved.getCapacity());
        assertSame(theater, saved.getTheater());
        assertNull(saved.getRoomType());
    }

    @Test
    void add_new_room_with_duplicated_name_throws_business_exception() {
        RoomNewRequest request = newRequest();
        when(roomRepository.existsByTheaterIdAndNameIgnoreCase(THEATER_ID, ROOM_NAME)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> roomAdminService.addNewRoom(request));

        assertEquals(ROOM_NAME_DUPLICATED, exception.getMessageKey());
        verify(roomRepository, never()).save(any());
    }

    @Test
    void update_room_with_name_of_another_room_throws_business_exception() {
        RoomNewRequest request = newRequest();
        when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(new Room()));
        when(roomRepository.existsByTheaterIdAndNameIgnoreCaseAndIdNot(THEATER_ID, ROOM_NAME, ROOM_ID)).thenReturn(true);

        assertThrows(BusinessException.class, () -> roomAdminService.updateRoom(ROOM_ID, request));
        verify(roomRepository, never()).save(any());
    }

    private static RoomNewRequest newRequest() {
        RoomNewRequest request = new RoomNewRequest();
        request.setName(" " + ROOM_NAME + " ");
        request.setTheaterId(THEATER_ID);
        request.setCapacity(40);
        return request;
    }
}
