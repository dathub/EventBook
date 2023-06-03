package com.dtcode.eventbook.service;

import com.dtcode.eventbook.web.model.AppUserDTO;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import com.dtcode.eventbook.web.model.EventBookItemDtoPage;
import org.springframework.data.domain.PageRequest;

public interface EventBookService {

    EventBookItemDTO saveEventBookItemForCurrentUser(EventBookItemDTO eventBookItemDTO);

    void updateEventBookItemForCurrentUser(Long eventBookItemId, EventBookItemDTO eventBookItemDTO);

    void deleteEventBookItemByIdForCurrentUser(Long eventBookItemId);

    EventBookItemDTO findEventBookItemByIdForCurrentUser(Long eventBookItemId);

    EventBookItemDtoPage findEventBookItemsForCurrentUser(PageRequest pageRequest, String date);

    EventBookItemDtoPage findEventBookItemsForAllUsers(PageRequest pageRequest, String date);

    //add user
    AppUserDTO addAppUser(AppUserDTO appUserDTO);
    //edit user
    void updateAppUser(Long userId, AppUserDTO appUserDTO);
    //delete user
    void deleteAppUser(Long userId);
}
