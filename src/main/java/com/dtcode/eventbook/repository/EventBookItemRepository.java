package com.dtcode.eventbook.repository;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.web.model.EventBookItemDtoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventBookItemRepository extends JpaRepository<EventBookItem, Long> {

    Page<EventBookItem> findAllByAppUser(AppUser appUser, Pageable pageable);

    Page<EventBookItem> findAllByAppUserAndDate(AppUser loggedInUser, LocalDate date, Pageable pageable);

    @Query("SELECT e FROM EventBookItem e WHERE e.appUser = :user AND e.date BETWEEN :startDate AND :endDate")
    Page<EventBookItem> findAllByAppUserAndDateBetween(@Param("user") AppUser user,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate, Pageable pageable);

    Page<EventBookItem> findAllByDate(LocalDate date, Pageable pageable);

    @Query("SELECT e FROM EventBookItem e WHERE e.date BETWEEN :startDate AND :endDate")
    Page<EventBookItem> findAllByDateBetween(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate, Pageable pageable);

}
