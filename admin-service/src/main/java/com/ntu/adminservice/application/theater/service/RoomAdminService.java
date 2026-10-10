package com.ntu.adminservice.application.theater.service;

import com.ntu.common.exception.BusinessException;
import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.moviecore.application.theater.dto.RoomAdminResponse;
import com.ntu.moviecore.application.theater.dto.RoomNewRequest;
import com.ntu.persistence.theater.entity.RoomJpaEntity;
import com.ntu.persistence.theater.entity.RoomTypeJpaEntity;
import com.ntu.persistence.theater.entity.TheaterJpaEntity;
import com.ntu.persistence.theater.repository.RoomJpaRepository;
import com.ntu.persistence.theater.repository.RoomTypeJpaRepository;
import com.ntu.persistence.theater.repository.TheaterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ntu.common.constant.AdminMessageKeyConstant.ROOM_NAME_DUPLICATED;

@Service
@RequiredArgsConstructor
public class RoomAdminService {

    private static final String ROOM = "Room";

    private final RoomRepository roomRepository;

    private final RoomTypeRepository roomTypeRepository;

    private final TheaterRepository theaterRepository;

    /**
     * @param theaterId theater to filter on, {@code null} for all theaters
     */
    @Transactional(readOnly = true)
    public List<RoomAdminResponse> getRooms(Long theaterId) {
        return roomRepository.findAdminRooms(theaterId)
                .stream()
                .map(RoomAdminService::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomAdminResponse getRoomById(long roomId) {
        return roomRepository.findById(roomId)
                .map(RoomAdminService::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ROOM, roomId));
    }

    @Transactional(readOnly = true)
    public List<RoomType> getRoomTypes() {
        return roomTypeRepository.findAll();
    }

    /**
     * @throws BusinessException when the theater already has a room with the same name
     */
    @Transactional
    public void addNewRoom(RoomNewRequest request) {
        String name = request.getName().trim();
        if (roomRepository.existsByTheaterIdAndNameIgnoreCase(request.getTheaterId(), name)) {
            throw new BusinessException(ROOM_NAME_DUPLICATED);
        }
        Room room = new Room();
        applyRequest(room, request, name);
        roomRepository.save(room);
    }

    /**
     * @throws BusinessException when another room of the theater already has the same name
     */
    @Transactional
    public void updateRoom(long roomId, RoomNewRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(ROOM, roomId));
        String name = request.getName().trim();
        if (roomRepository.existsByTheaterIdAndNameIgnoreCaseAndIdNot(request.getTheaterId(), name, roomId)) {
            throw new BusinessException(ROOM_NAME_DUPLICATED);
        }
        applyRequest(room, request, name);
        roomRepository.save(room);
    }

    private void applyRequest(Room room, RoomNewRequest request, String name) {
        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater", request.getTheaterId()));
        RoomType roomType = request.getRoomTypeId() == null
                ? null
                : roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Room type", request.getRoomTypeId()));

        room.setName(name);
        room.setTheater(theater);
        room.setRoomType(roomType);
        room.setCapacity(request.getCapacity());
    }

    private static RoomAdminResponse toResponse(Room room) {
        RoomAdminResponse response = new RoomAdminResponse();
        response.setRoomId(room.getId());
        response.setName(room.getName());
        response.setCapacity(room.getCapacity());
        if (room.getTheater() != null) {
            response.setTheaterId(room.getTheater().getId());
            response.setTheaterName(room.getTheater().getName());
        }
        if (room.getRoomType() != null) {
            response.setRoomTypeId(room.getRoomType().getId());
            response.setRoomTypeName(room.getRoomType().getName());
        }
        return response;
    }
}
