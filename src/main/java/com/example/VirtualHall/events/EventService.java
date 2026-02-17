package com.example.VirtualHall.events;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getFilteredEvents(List<String> dateFilters, List<Long> eventTypeIds) {
        // Если фильтры не выбраны - возвращаем все
        if ((dateFilters == null || dateFilters.isEmpty()) &&
                (eventTypeIds == null || eventTypeIds.isEmpty())) {
            return getAllEvents();
        }

        List<Event> result = new ArrayList<>();

        // Если выбраны только типы мероприятий (без фильтра по дате)
        if ((dateFilters == null || dateFilters.isEmpty()) &&
                eventTypeIds != null && !eventTypeIds.isEmpty()) {
            return eventRepository.findByEventType_EventTypeIdIn(eventTypeIds);
        }

        // Если выбраны только фильтры по дате (без типов)
        if (dateFilters != null && !dateFilters.isEmpty() &&
                (eventTypeIds == null || eventTypeIds.isEmpty())) {

            for (String filter : dateFilters) {
                LocalDateTime[] dateRange = getDateRange(filter);
                if (dateRange != null) {
                    result.addAll(eventRepository.findByDateTimeBetween(
                            dateRange[0], dateRange[1]
                    ));
                }
            }
            return result;
        }

        // Если выбраны и даты, и типы
        if (dateFilters != null && !dateFilters.isEmpty() &&
                eventTypeIds != null && !eventTypeIds.isEmpty()) {

            for (String filter : dateFilters) {
                LocalDateTime[] dateRange = getDateRange(filter);
                if (dateRange != null) {
                    result.addAll(eventRepository.findByDateTimeBetweenAndEventType_EventTypeIdIn(
                            dateRange[0], dateRange[1], eventTypeIds
                    ));
                }
            }
            return result;
        }

        return result;
    }

    private LocalDateTime[] getDateRange(String filter) {
        LocalDateTime start;
        LocalDateTime end;
        LocalDate today = LocalDate.now();

        switch (filter) {
            case "today":
                start = today.atStartOfDay();
                end = today.atTime(LocalTime.MAX);
                break;

            case "tomorrow":
                LocalDate tomorrow = today.plusDays(1);
                start = tomorrow.atStartOfDay();
                end = tomorrow.atTime(LocalTime.MAX);
                break;

            case "thisWeek":
                LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
                LocalDate endOfWeek = startOfWeek.plusDays(6);
                start = startOfWeek.atStartOfDay();
                end = endOfWeek.atTime(LocalTime.MAX);
                break;

            case "thisMonth":
                LocalDate startOfMonth = today.withDayOfMonth(1);
                LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
                start = startOfMonth.atStartOfDay();
                end = endOfMonth.atTime(LocalTime.MAX);
                break;

            default:
                return null;
        }

        return new LocalDateTime[]{start, end};
    }

    @Transactional
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
}