package com.dtcode.eventbook.repository;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.domain.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventBookItemRepository extends JpaRepository<EventBookItem, Long> {

    Page<EventBookItem> findAllByAppUser(AppUser appUser, Pageable pageable);

    //Query by date, year, year and month
}
