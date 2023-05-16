package com.dtcode.eventbook.web.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class EventBookItemDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String userName;
}
