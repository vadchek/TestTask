package com.vadim.calendar.db;

import com.vadim.calendar.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByFromBetweenOrToBetween(LocalDateTime start, LocalDateTime finish, LocalDateTime start1, LocalDateTime finish1);
}
