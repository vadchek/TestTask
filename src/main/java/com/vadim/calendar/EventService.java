package com.vadim.calendar;

import com.vadim.calendar.db.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class EventService {

    private Integer numberOfWeek = 0;/*the difference in weeks between the displayed week and the current week */
    private LocalDate startOfWeek; /*start date of the displayed week */

    @Autowired
    private EventRepository repository;

    /**
     * This method validates data entered by user
     * If data is correct and there is no time overlap with other events, a new event is created
     */
    public void save(LocalDateTime from, LocalDateTime to, String description, String creator, String title) {

        if (to.isBefore(from)) {
            throw new IllegalArgumentException("From " + from + "can't be before to " + to);
        }

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
        event.setCreator(creator);
        event.setTitle(title);
        repository.save(event);
    }

    /**
     * This method returns list of events of one week
     */
    public List<Event> getWeek(Integer counter) {
        numberOfWeek += counter;
        LocalDate now = LocalDate.now();
        LocalDateTime to = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay().plusWeeks(numberOfWeek);
        LocalDateTime from = to.minusWeeks(1);
        startOfWeek = from.toLocalDate();
        return repository.findByFromBetweenOrToBetween(from,to,from,to);
    }

    public LocalDate getStartOfWeek(){
        return startOfWeek;
    }

}
