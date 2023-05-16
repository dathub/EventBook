package com.dtcode.eventbook.service;

import com.dtcode.eventbook.web.model.EventBookItemDTO;

import java.time.LocalDate;

public interface EventBookItemService {

    EventBookItemDTO saveEventBookItem(EventBookItemDTO eventBookItemDTO);

    EventBookItemDTO findEventBookItemById(Long eventBookItemId);
}
