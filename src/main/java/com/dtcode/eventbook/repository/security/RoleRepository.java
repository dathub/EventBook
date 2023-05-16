package com.dtcode.eventbook.repository.security;

import com.dtcode.eventbook.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
