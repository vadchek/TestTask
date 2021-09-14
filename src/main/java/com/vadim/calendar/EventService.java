package com.vadim.calendar;

import com.vadim.calendar.db.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class EventService {

    private Integer numberOfWeek = 0;
    @Autowired
    private EventRepository repository;

    public void save(LocalDateTime from, LocalDateTime to, String description) {

        if (Duration.between(from,to).toMinutes() < 30 || Duration.between(from,to).toHours() > 24){
            throw new IllegalArgumentException("Time range should be more than 30 minutes and less than 24 hour");
        }

        LocalDateTime now = LocalDateTime.now();
        if (from.isBefore(now)) {
            throw new IllegalArgumentException("Time range should be in future");
        }

        List<Event> events = repository.findByFromBetweenOrToBetween(from,to,from,to);

        for (Event event : events) {
            if (!event.getFrom().isEqual(to) && !event.getTo().isEqual(from)) {
                throw new IllegalArgumentException("Time range is busy");
            }
        }

        Event event = new Event();
        event.setFrom(from);
        event.setTo(to);
        event.setDescription(description);
        repository.save(event);
    }

    public List<Event> getWeek(Integer n) {
        numberOfWeek += n;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).plusWeeks(numberOfWeek);
        LocalDateTime from = to.minusWeeks(1);
        return repository.findByFromBetweenOrToBetween(from,to,from,to);
    }
}
