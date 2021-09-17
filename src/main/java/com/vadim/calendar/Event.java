package com.vadim.calendar;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This class describes the events that the user creates
 * Objects of this class are fields of the event table
 */

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private LocalDateTime from;
    @Column(name = "finish")
    private LocalDateTime to;
    @Column(name = "details")
    private String description;
    @Column(name = "creator")
    private String creator;
    @Column(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails(){
        return "Title: " + title
                + "\n Description: " + description
                + "\n Start time: " + from
                + "\n End time: " + to
                + "\n Creator: " + creator;
    }
}
