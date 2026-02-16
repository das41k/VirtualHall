package com.example.VirtualHall.events;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

// ПОЗЖЕ ДОБАВИТЬ ВАЛИДАЦИЮ

@Entity
@Table(name = "event")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id")
    private Long eventId;

    @Column(name = "title", nullable = false, length = 100) // 100 размер
    private String title;

    @Column(name = "description", length = 300) // 300 размер
    private String description;

    @ManyToOne
    @JoinColumn(name = "eventType_id", nullable = false)
    private EventType eventType;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "intro_image")
    private String introImagePath;
}
