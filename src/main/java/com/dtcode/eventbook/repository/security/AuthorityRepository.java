package com.dtcode.eventbook.repository.security;

import com.dtcode.eventbook.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}