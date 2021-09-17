package com.vadim.calendar.web;

import com.vadim.calendar.Event;
import com.vadim.calendar.EventService;
import com.vadim.calendar.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping("/event")
    public String event() {
        return "event";
    }

    @GetMapping("/")
    public String main() {
        return "redirect:/events";
    }

    @PostMapping("/event")
    public String save(@AuthenticationPrincipal User user,
                       @RequestParam(defaultValue = "") String sFrom, @RequestParam(defaultValue = "") String sTo,
                       @RequestParam(defaultValue = "") String description, @RequestParam(defaultValue = "") String title,
                       @RequestParam(defaultValue = "") String message, Model model) {

        if (sFrom.equals("") || sTo.equals("")) {
            model.addAttribute("message", "enter start time and end time");
            return "event";
        }
        if(title.equals("")){
            model.addAttribute("message", "enter title");
        }

        LocalDateTime from = LocalDateTime.parse(sFrom, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime to = LocalDateTime.parse(sTo, DateTimeFormatter.ISO_DATE_TIME);
        String creator = user.getUsername();

        try {
            service.save(from, to, description, creator, title);
        } catch (IllegalArgumentException exception) {
            message = exception.getMessage();
            model.addAttribute("message", message);
            return "event";
        }
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String getWeek(@RequestParam(defaultValue = "0") Integer counter, Model model) {
        List<Event> events = service.getWeek(counter);
        LocalDate startOfWeek = service.getStartOfWeek();
        List<EventRectangle> rectangles = new ArrayList<>();
        for (Event event : events) {
            if (event.getFrom().getDayOfWeek().equals(event.getTo().getDayOfWeek())) {
                rectangles.add(new EventRectangle(
                        event.getFrom().getDayOfWeek().getValue(),
                        event.getFrom().toLocalTime(),
                        Duration.between(event.getFrom(), event.getTo()),
                        event.getDetails(),
                        event.getTitle()
                ));
            }
            else {
                if (!event.getTo().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    rectangles.add(new EventRectangle(
                            event.getFrom().getDayOfWeek().getValue(),
                            event.getFrom().toLocalTime(),
                            Duration.between(event.getFrom(), event.getTo().toLocalDate().atStartOfDay()),
                            event.getDetails(),
                            event.getTitle()
                    ));
                    rectangles.add(new EventRectangle(
                            event.getTo().getDayOfWeek().getValue(),
                            event.getTo().toLocalDate().atStartOfDay().toLocalTime(),
                            Duration.between(event.getTo().toLocalDate().atStartOfDay(), event.getTo()),
                            event.getDetails(),
                            event.getTitle()
                    ));
                }
                else {
                    if(event.getTo().toLocalDate().equals(startOfWeek)){
                        rectangles.add(new EventRectangle(
                                event.getTo().getDayOfWeek().getValue(),
                                event.getTo().toLocalDate().atStartOfDay().toLocalTime(),
                                Duration.between(event.getTo().toLocalDate().atStartOfDay(), event.getTo()),
                                event.getDetails(),
                                event.getTitle()
                        ));
                    }
                    else{
                        rectangles.add(new EventRectangle(
                                event.getFrom().getDayOfWeek().getValue(),
                                event.getFrom().toLocalTime(),
                                Duration.between(event.getFrom(), event.getTo().toLocalDate().atStartOfDay()),
                                event.getDetails(),
                                event.getTitle()
                        ));
                    }
                }
            }
        }
        model.addAttribute("rectangles", rectangles);
        model.addAttribute("monday", startOfWeek);
        model.addAttribute("tuesday", startOfWeek.plusDays(1));
        model.addAttribute("wednesday", startOfWeek.plusDays(2));
        model.addAttribute("thursday", startOfWeek.plusDays(3));
        model.addAttribute("friday", startOfWeek.plusDays(4));
        model.addAttribute("saturday", startOfWeek.plusDays(5));
        model.addAttribute("sunday", startOfWeek.plusDays(6));
        return "events";
    }

}