package com.example.VirtualHall.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventController(EventService eventService, EventTypeRepository eventTypeRepository) {
        this.eventService = eventService;
        this.eventTypeRepository = eventTypeRepository;
    }

    @GetMapping
    public String getFilteredEvents(
            @RequestParam(required = false) List<String> dateFilters,
            @RequestParam(required = false) List<Long> eventTypeIds,
            Model model) {

        List<Event> events = eventService.getFilteredEvents(dateFilters, eventTypeIds);
        List<EventType> allEventTypes = eventTypeRepository.findAll();

        model.addAttribute("events", events);
        model.addAttribute("allEventTypes", allEventTypes);
        model.addAttribute("selectedDateFilters", dateFilters);
        model.addAttribute("selectedEventTypeIds", eventTypeIds);

        return "events/eventList";
    }
}
