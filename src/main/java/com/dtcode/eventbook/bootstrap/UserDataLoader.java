package com.dtcode.eventbook.bootstrap;

import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.domain.security.Authority;
import com.dtcode.eventbook.domain.security.Role;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import com.dtcode.eventbook.repository.security.AuthorityRepository;
import com.dtcode.eventbook.repository.security.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {

        //Create Authorities
        Authority createEventBookItem = authorityRepository.save(Authority.builder().permission("eventbookitem.create").build());
        Authority readEventBookItem = authorityRepository.save(Authority.builder().permission("eventbookitem.read").build());
        Authority updateEventBookItem = authorityRepository.save(Authority.builder().permission("eventbookitem.update").build());
        Authority deleteEventBookItem = authorityRepository.save(Authority.builder().permission("eventbookitem.delete").build());

        Authority createAppUser = authorityRepository.save(Authority.builder().permission("appuser.create").build());
        Authority readAppUser = authorityRepository.save(Authority.builder().permission("appuser.read").build());
        Authority updateAppUser = authorityRepository.save(Authority.builder().permission("appuser.update").build());
        Authority deleteAppUser = authorityRepository.save(Authority.builder().permission("appuser.delete").build());

        //Create Roles
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        adminRole.setAuthorities(new HashSet<>(Set.of(createEventBookItem, readEventBookItem, updateEventBookItem, deleteEventBookItem,
                createAppUser, readAppUser, updateAppUser, deleteAppUser)));
        userRole.setAuthorities(new HashSet<>(Set.of(createEventBookItem, readEventBookItem, updateEventBookItem, deleteEventBookItem)));

        roleRepository.saveAll(Arrays.asList(adminRole, userRole));


        //Create Users
        AppUser appUserAdmin = appUserRepository.save(AppUser.builder()
                .userName("TestAdmin")
                .password(passwordEncoder.encode("ad123"))
                .build());
        appUserAdmin.setRoles(new HashSet<>(Set.of(adminRole)));
        appUserRepository.save(appUserAdmin);

        AppUser appUserUser = appUserRepository.save(AppUser.builder()
                .userName("TestUser")
                .password(passwordEncoder.encode("us123"))
                .build());
        appUserUser.setRoles(new HashSet<>(Set.of(userRole)));
        appUserRepository.save(appUserUser);


        log.debug("Users Loaded: " + appUserRepository.count());
    }
}
