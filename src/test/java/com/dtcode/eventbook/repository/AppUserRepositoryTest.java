package com.dtcode.eventbook.repository;

import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository underTestAppUserRepository;
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    void findByUserNameWhenUserExistsTest() {

        String userName = "TestUser";

        //Given
        when(passwordEncoder.encode("test")).thenReturn("encodedpw-test");
        AppUser testUser = underTestAppUserRepository.save(AppUser.builder()
                .userName(userName)
                .password(passwordEncoder.encode("test"))
                .build());

        //When
        Optional<AppUser> byUserName = underTestAppUserRepository.findByUserName(userName);

        //Then
        assertThat(byUserName.isPresent()).isTrue();
    }

    @Test
    void findByUserNameWhenUserDoesNotExistTest() {

        String userName = "TestUser";
        //When
        Optional<AppUser> byUserName = underTestAppUserRepository.findByUserName(userName);

        //Then
        assertThat(byUserName.isPresent()).isFalse();
    }
}
