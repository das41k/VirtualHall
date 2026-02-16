package com.example.VirtualHall.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventType_EventTypeIdIn(List<Long> eventTypeIds);
    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByDateTimeBetweenAndEventType_EventTypeIdIn(
            LocalDateTime start,
            LocalDateTime end,
            List<Long> eventTypeIds
    );
}