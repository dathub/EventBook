package com.dtcode.eventbook.service;

import com.dtcode.eventbook.web.model.EventBookItemDTO;
import com.dtcode.eventbook.web.model.EventBookItemDtoPage;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public interface EventBookItemService {

    EventBookItemDTO saveEventBookItem(EventBookItemDTO eventBookItemDTO);

    EventBookItemDTO findEventBookItemById(Long eventBookItemId);

    EventBookItemDtoPage findAllEventBookItems(PageRequest pageRequest);

    void updateEventBookItem(Long eventBookItemId, EventBookItemDTO eventBookItemDTO);

    void deleteById(Long eventBookItemId);
}
