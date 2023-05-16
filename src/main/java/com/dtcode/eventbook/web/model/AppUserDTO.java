package com.dtcode.eventbook.web.model;

import com.dtcode.eventbook.domain.EventBookItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AppUserDTO {
    private String userName;
    private List<EventBookItem> eventBookItems;
}
