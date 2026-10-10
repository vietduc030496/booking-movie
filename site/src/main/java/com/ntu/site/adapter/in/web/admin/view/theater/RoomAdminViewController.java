package com.ntu.site.adapter.in.web.admin.view.theater;

import com.ntu.adminservice.application.theater.service.RoomAdminService;
import com.ntu.adminservice.application.theater.service.TheaterAdminService;
import com.ntu.moviecore.application.theater.dto.RoomNewRequest;
import com.ntu.site.adapter.in.web.admin.view.BaseAdminViewController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.ntu.common.constant.AdminMessageKeyConstant.SAVE_SUCCESS;
import static com.ntu.common.constant.UrlConstant.ADMIN_ROOM_VIEW_URL;

@Controller
@RequestMapping(ADMIN_ROOM_VIEW_URL)
@RequiredArgsConstructor
public class RoomAdminViewController extends BaseAdminViewController {

    private static final String ROOM = "room";
    private static final String ROOM_MODAL_FRAGMENT = "roomModalFragment";

    private final RoomAdminService roomAdminService;

    private final TheaterAdminService theaterAdminService;

    @GetMapping
    public String getRoomsPage(@RequestParam(value = "theaterId", required = false) Long theaterId,
                               Model model) {
        model.addAttribute("rooms", roomAdminService.getRooms(theaterId));
        model.addAttribute("theaters", theaterAdminService.getTheaters());
        model.addAttribute("selectedTheaterId", theaterId);
        return "admin/theater/rooms";
    }

    @GetMapping("/{roomId}")
    public String getRoomDetailPage(@PathVariable("roomId") long roomId, Model model) {
        addFormAttributes(model, roomAdminService.getRoomById(roomId), ADMIN_ROOM_VIEW_URL + "/" + roomId);
        return fragment(ADMIN_FRAGMENT_FILE, ROOM_MODAL_FRAGMENT);
    }

    @PostMapping("/{roomId}")
    public String updateRoom(@PathVariable("roomId") long roomId,
                             @Valid @ModelAttribute(ROOM) RoomNewRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_ROOM_VIEW_URL, SAVE_SUCCESS,
                () -> roomAdminService.updateRoom(roomId, request));
    }

    @GetMapping(ADD_NEW_PATH)
    public String getAddNewRoomPage(@RequestParam(value = "theaterId", required = false) Long theaterId,
                                    Model model) {
        RoomNewRequest room = new RoomNewRequest();
        room.setTheaterId(theaterId);
        addFormAttributes(model, room, ADMIN_ROOM_VIEW_URL + ADD_NEW_PATH);
        return fragment(ADMIN_FRAGMENT_FILE, ROOM_MODAL_FRAGMENT);
    }

    @PostMapping(ADD_NEW_PATH)
    public String addNewRoom(@Valid @ModelAttribute(ROOM) RoomNewRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_ROOM_VIEW_URL, SAVE_SUCCESS,
                () -> roomAdminService.addNewRoom(request));
    }

    private void addFormAttributes(Model model, Object room, String actionUrl) {
        model.addAttribute(ROOM, room);
        model.addAttribute("theaters", theaterAdminService.getTheaters());
        model.addAttribute("roomTypes", roomAdminService.getRoomTypes());
        model.addAttribute(ACTION_URL, actionUrl);
    }
}
