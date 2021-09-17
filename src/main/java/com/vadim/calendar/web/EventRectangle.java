package com.vadim.calendar.web;

import java.time.Duration;
import java.time.LocalTime;

/**
 * This class is required to display events
 */

public class EventRectangle {
    private final int weekDay;
    private final LocalTime start;
    private final Duration duration;
    private final String description;
    private final String title;

    public EventRectangle(int weekDay, LocalTime start, Duration duration, String description, String title) {
        this.weekDay = weekDay;
        this.start = start;
        this.duration = duration;
        this.description = description;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public LocalTime getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "EventRectangle{" +
                "weekDay=" + weekDay +
                ", start=" + start +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                '}';
    }
}