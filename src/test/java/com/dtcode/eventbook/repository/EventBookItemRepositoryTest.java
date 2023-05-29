package com.dtcode.eventbook.repository;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

@DataJpaTest
public class EventBookItemRepositoryTest {

    @Autowired
    private EventBookItemRepository underTestEventBookItemRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    void findAllByAppUserTest() {

        when(passwordEncoder.encode("test")).thenReturn("encodedpw-test");

        //Given
        AppUser testUser = appUserRepository.save(AppUser.builder()
                .userName("TestUser")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023,3,10))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022,2,12))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022,2,12))
                .appUser(testUser)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository.findAllByAppUser(testUser, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(3);
    }


    @Test
    void findAllByAppUserAndDateTest() {

        when(passwordEncoder.encode("test")).thenReturn("encodedpw-test");

        //Given
        AppUser testUser = appUserRepository.save(AppUser.builder()
                .userName("TestUser")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023, 3, 10))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022, 6, 22))
                .appUser(testUser)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate date = LocalDate.of(2022, 2, 12);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository
                .findAllByAppUserAndDate(testUser, date, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(1);
        assertThat(allEventBookItemsByAppUser.get().filter(i -> i.getDate().isEqual(date)).count()).isEqualTo(1);
    }

    @Test
    void findAllByAppUserAndDateBetweenTest() {

        when(passwordEncoder.encode("test")).thenReturn("encodedpw-test");

        //Given
        AppUser testUser = appUserRepository.save(AppUser.builder()
                .userName("TestUser")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023, 3, 10))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022, 6, 22))
                .appUser(testUser)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository
                .findAllByAppUserAndDateBetween(testUser, startDate, endDate, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(2);
    }

}
