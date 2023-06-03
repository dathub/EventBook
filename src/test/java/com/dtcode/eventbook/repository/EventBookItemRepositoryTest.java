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
import static org.mockito.ArgumentMatchers.anyString;
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

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpw-test");

        //Given
        AppUser testUser1 = appUserRepository.save(AppUser.builder()
                .userName("TestUser1")
                .password(passwordEncoder.encode("test"))
                .build());

        AppUser testUser2 = appUserRepository.save(AppUser.builder()
                .userName("TestUser2")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023,3,10))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022,2,12))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022,2,12))
                .appUser(testUser2)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository.findAllByAppUser(testUser1, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(2);
    }


    @Test
    void findAllByAppUserAndDateTest() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpw-test");

        //Given
        AppUser testUser1 = appUserRepository.save(AppUser.builder()
                .userName("TestUser1")
                .password(passwordEncoder.encode("test"))
                .build());

        AppUser testUser2 = appUserRepository.save(AppUser.builder()
                .userName("TestUser2")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023, 3, 10))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2023, 3, 10))
                .appUser(testUser2)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2023, 6, 22))
                .appUser(testUser2)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate date = LocalDate.of(2023, 3, 10);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository
                .findAllByAppUserAndDate(testUser1, date, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(1);
        assertThat(allEventBookItemsByAppUser.get().filter(i -> i.getDate().isEqual(date) && i.getAppUser().equals(testUser1)).count()).isEqualTo(1);
    }

    @Test
    void findAllByAppUserAndDateBetweenTest() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpwd");

        //Given
        AppUser testUser1 = appUserRepository.save(AppUser.builder()
                .userName("TestUser1")
                .password(passwordEncoder.encode("test"))
                .build());

        AppUser testUser2 = appUserRepository.save(AppUser.builder()
                .userName("TestUser2")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023, 3, 10))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022, 6, 22))
                .appUser(testUser2)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository
                .findAllByAppUserAndDateBetween(testUser1, startDate, endDate, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(1);
        assertThat(allEventBookItemsByAppUser.get().filter(i -> i.getAppUser().equals(testUser1)).count()).isEqualTo(1);
    }

    @Test
    void findAllByDateTest() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpwd");

        //Given
        AppUser testUser1 = appUserRepository.save(AppUser.builder()
                .userName("TestUser1")
                .password(passwordEncoder.encode("test"))
                .build());

        AppUser testUser2 = appUserRepository.save(AppUser.builder()
                .userName("TestUser2")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser2)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022, 6, 22))
                .appUser(testUser1)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate date = LocalDate.of(2022, 2, 12);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItems = underTestEventBookItemRepository
                .findAllByDate(date, pageRequest);

        //Then
        assertThat(allEventBookItems.getTotalElements()).isEqualTo(2);
        assertThat(allEventBookItems.get().filter(i -> i.getDate().isEqual(date)).count()).isEqualTo(2);
    }

    @Test
    void findAllByDateBetweenTest() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpwd");

        //Given
        AppUser testUser1 = appUserRepository.save(AppUser.builder()
                .userName("TestUser1")
                .password(passwordEncoder.encode("test"))
                .build());

        AppUser testUser2 = appUserRepository.save(AppUser.builder()
                .userName("TestUser2")
                .password(passwordEncoder.encode("test"))
                .build());

        EventBookItem eventBookItem1 = EventBookItem
                .builder()
                .title("Test item1")
                .description("Test description1")
                .date(LocalDate.of(2023, 2, 12))
                .appUser(testUser1)
                .build();

        EventBookItem eventBookItem2 = EventBookItem
                .builder()
                .title("Test item2")
                .description("Test description2")
                .date(LocalDate.of(2022, 2, 12))
                .appUser(testUser2)
                .build();

        EventBookItem eventBookItem3 = EventBookItem
                .builder()
                .title("Test item3")
                .description("Test description3")
                .date(LocalDate.of(2022, 6, 22))
                .appUser(testUser1)
                .build();

        underTestEventBookItemRepository.save(eventBookItem1);
        underTestEventBookItemRepository.save(eventBookItem2);
        underTestEventBookItemRepository.save(eventBookItem3);

        //When
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EventBookItem> allEventBookItemsByAppUser = underTestEventBookItemRepository
                .findAllByDateBetween(startDate, endDate, pageRequest);

        //Then
        assertThat(allEventBookItemsByAppUser.getTotalElements()).isEqualTo(2);
    }

}
