package com.dtcode.eventbook.repository;

import com.dtcode.eventbook.domain.EventBookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventBookItemRepository extends JpaRepository<EventBookItem, Long> {
}
