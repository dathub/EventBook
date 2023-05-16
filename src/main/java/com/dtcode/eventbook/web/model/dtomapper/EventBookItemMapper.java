package com.dtcode.eventbook.web.model.dtomapper;


import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AppUserMapper.class})
public interface EventBookItemMapper {

    EventBookItemMapper INSTANCE = Mappers.getMapper(EventBookItemMapper.class);

    @Mapping(source = "eventBookItem.id", target = "id")
    @Mapping(source = "eventBookItem.appUser.userName", target = "userName")
    EventBookItemDTO eventBookItemToDto(EventBookItem eventBookItem);

    @Mapping(source = "appUser", target = "appUser")
    @Mapping(source = "eventBookItemDTO.id", target = "id")
    EventBookItem dtoToEventBookItem(EventBookItemDTO eventBookItemDTO, AppUser appUser);

}
