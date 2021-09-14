package com.vadim.calendar.web;

import java.time.LocalDateTime;

public class EventRequest {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String description;

    public EventRequest(LocalDateTime from, LocalDateTime to, String description) {
        this.from = from;
        this.to = to;
        this.description = description;


        if (to.isBefore(from)) {
            throw new IllegalArgumentException("From " + from + "can't be before to " + to);
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }
}