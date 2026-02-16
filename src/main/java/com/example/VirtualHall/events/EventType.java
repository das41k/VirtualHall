package com.example.VirtualHall.events;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "eventType")
@Data
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventTypeId;

    @Column(name = "name", nullable = false, unique = true, length = 70) // 70 размер
    private String name;

}
