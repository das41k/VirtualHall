package com.example.VirtualHall.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String getEventById(@PathVariable("id") Long eventId, Model model,
                               RedirectAttributes redirectAttributes) {
        Optional<Event> event = eventService.getEventById(eventId);
        if (event.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Данное мероприятие не было найдено в системе!");
            return "redirect:/events";
        }
        model.addAttribute("event",event.get());
        return "events/eventPage";
    }
}
