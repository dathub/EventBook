package com.dtcode.eventbook.web.model.dtomapper;

import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.web.model.AppUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {EventBookItemMapper.class})
public interface AppUserMapper {

    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(source = "eventBookItems", target = "eventBookItems")
    AppUserDTO appUserToDto(AppUser appUser, List<EventBookItem> eventBookItems);
}
