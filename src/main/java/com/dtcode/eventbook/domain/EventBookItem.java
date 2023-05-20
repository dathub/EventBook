package com.dtcode.eventbook.domain;


import com.dtcode.eventbook.domain.security.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EventBookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user")
    private AppUser appUser;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @Temporal(TemporalType.DATE)
    private LocalDate date;
}
