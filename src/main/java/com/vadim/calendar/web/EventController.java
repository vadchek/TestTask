package com.vadim.calendar.web;

import com.vadim.calendar.Event;
import com.vadim.calendar.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping("/event")
    public String event() { return "event"; }

    @GetMapping("/")
    public String main() { return "events"; }

    @PostMapping("/event")
    public String save(@RequestParam(defaultValue = "") String sFrom, @RequestParam(defaultValue = "") String sTo,
                       @RequestParam(defaultValue = "") String description, @RequestParam(defaultValue = "") String message, Model model) {

        if(sFrom.equals("") || sTo.equals("")){
            model.addAttribute("message", "enter start time and end time");
            return "event";
        }
        LocalDateTime from = LocalDateTime.parse(sFrom, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime to = LocalDateTime.parse(sTo, DateTimeFormatter.ISO_DATE_TIME);
        EventRequest request = new EventRequest(from, to, description);
        try {
            service.save(request.getFrom(), request.getTo(), request.getDescription());
        }catch (IllegalArgumentException exception){
            message = exception.getMessage();
            model.addAttribute("message", message);
            return "event";
        }
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String getAll(@RequestParam(defaultValue = "0") Integer n, Model model) {
        List<Event> events = service.getWeek(n);
        List<String> rectangles = new ArrayList<>();
        String str = "";
        for (Event event: events) {
            int x = 100 + event.getFrom().getDayOfWeek().getValue() * 150;
            int y = 50 + (event.getFrom().getHour() + event.getFrom().getMinute() / 60) * 15;
            int height = (event.getTo().getHour() + event.getTo().getMinute() / 60) * 15 - (event.getFrom().getHour() + event.getFrom().getMinute() / 60) * 15;
            rectangles.add("x=\"" + x + "\" y=\"" + y +"\" width=\"140\" height=\""+ height +"\" style=\"fill:red;stroke:black;stroke-width:5;opacity:0.5\"");
            str = "x=\"" + x + "\" y=\"" + y +"\" width=\"140\" height=\""+ height +"\" style=\"fill:red;stroke:black;stroke-width:5;opacity:0.5\"";
        }
        model.addAttribute("rectangles", str);
        return "events";
    }

}