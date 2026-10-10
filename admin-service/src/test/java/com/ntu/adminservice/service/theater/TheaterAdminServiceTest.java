package com.ntu.adminservice.service.theater;

import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.moviecore.domain.material.entity.Ward;
import com.ntu.moviecore.domain.material.repository.WardRepository;
import com.ntu.moviecore.domain.theater.dto.TheaterNewRequest;
import com.ntu.moviecore.domain.theater.entity.Theater;
import com.ntu.moviecore.domain.theater.repository.TheaterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TheaterAdminServiceTest {

    private static final long THEATER_ID = 5L;
    private static final long WARD_ID = 7L;

    @Mock
    private WardRepository wardRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private TheaterAdminService theaterAdminService;

    @Test
    void add_new_default_theater_removes_default_flag_of_other_theaters() {
        Ward ward = new Ward();
        when(wardRepository.findById(WARD_ID)).thenReturn(Optional.of(ward));
        when(theaterRepository.save(any(Theater.class))).thenAnswer(invocation -> {
            Theater theater = invocation.getArgument(0);
            theater.setId(THEATER_ID);
            return theater;
        });

        theaterAdminService.addNewTheater(newRequest(true));

        ArgumentCaptor<Theater> captor = ArgumentCaptor.forClass(Theater.class);
        verify(theaterRepository).save(captor.capture());
        Theater saved = captor.getValue();
        assertEquals("NTU Cinema", saved.getName());
        assertEquals("1 Nguyen Trai", saved.getAddress());
        assertEquals("19001000", saved.getHotline());
        assertSame(ward, saved.getWard());
        assertTrue(saved.isDefault());
        verify(theaterRepository).clearDefaultExcept(THEATER_ID);
    }

    @Test
    void update_theater_not_default_keeps_other_theaters() {
        Theater theater = new Theater();
        theater.setId(THEATER_ID);
        when(theaterRepository.findById(THEATER_ID)).thenReturn(Optional.of(theater));
        when(wardRepository.findById(WARD_ID)).thenReturn(Optional.of(new Ward()));
        when(theaterRepository.save(theater)).thenReturn(theater);

        theaterAdminService.updateTheater(THEATER_ID, newRequest(false));

        verify(theaterRepository, never()).clearDefaultExcept(anyLong());
    }

    @Test
    void update_unknown_theater_throws_not_found() {
        TheaterNewRequest request = newRequest(false);
        when(theaterRepository.findById(THEATER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> theaterAdminService.updateTheater(THEATER_ID, request));
        verify(theaterRepository, never()).save(any());
    }

    private static TheaterNewRequest newRequest(boolean defaultFlag) {
        TheaterNewRequest request = new TheaterNewRequest();
        request.setName(" NTU Cinema ");
        request.setAddress("1 Nguyen Trai");
        request.setHotline("19001000");
        request.setWardId(WARD_ID);
        request.setDefaultFlag(defaultFlag);
        return request;
    }
}
