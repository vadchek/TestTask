package com.vadim.calendar.web;

import java.time.LocalDateTime;

public class EventResponse {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String description;

    public EventResponse(LocalDateTime from, LocalDateTime to, String description) {
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getDescription() { return description; }
}