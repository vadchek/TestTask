package com.vadim.calendar.db;

import com.vadim.calendar.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * This method returns list of events overlapping in time with the range
     * startFrom = startTo = start of range
     * finishFrom = finishTo = end of range
     */
    List<Event> findByFromBetweenOrToBetween(LocalDateTime startFrom, LocalDateTime finishFrom, LocalDateTime startTo, LocalDateTime finishTo);
}
