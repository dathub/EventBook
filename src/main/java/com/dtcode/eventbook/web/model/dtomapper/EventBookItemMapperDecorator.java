package com.dtcode.eventbook.web.model.dtomapper;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class EventBookItemMapperDecorator implements EventBookItemMapper{

    private EventBookItemMapper eventBookItemMapper;

    @Autowired
    @Qualifier("delegate")
    public void setEventBookItemMapper(EventBookItemMapper eventBookItemMapper) {
        this.eventBookItemMapper = eventBookItemMapper;
    }

    @Override
    public EventBookItemDTO eventBookItemToDto(EventBookItem eventBookItem){

        EventBookItemDTO eventBookItemDTO = eventBookItemMapper.eventBookItemToDto(eventBookItem);

        AppUser appUser = eventBookItem.getAppUser();
        if(appUser != null) {
            eventBookItemDTO.setUserName(appUser.getUsername());
        }

        return eventBookItemDTO;
    }

}
