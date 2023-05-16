package com.dtcode.eventbook.security;

import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.domain.security.Authority;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Getting User info via JPA");

        AppUser appUser = appUserRepository.findByUserName(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User name: " + username + " not found");
        });

        return new org.springframework.security.core.userdetails.User(appUser.getUserName(), appUser.getPassword(),
                appUser.getEnabled(), appUser.getAccountNonExpired(), appUser.getCredentialsNonExpired(),
                appUser.getAccountNonLocked(), convertToSpringAuthorities(appUser.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0){
            return authorities.stream()
                    .map(Authority::getPermission)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}
