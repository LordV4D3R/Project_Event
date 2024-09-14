package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "event")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "event_organizer_id")
    private EventOrganizer eventOrganizer;

    private String eventName;
    private String eventDescription;
    private Date createOn;
    private Date updateOn;
    private String eventLocation;
    private int eventCapacity;
    private Date startOn;
    private Date endOn;

    private eventType eventType;
    private eventStatus eventStatus;
    public enum eventType {
        PUBLIC,
        PRIVATE
    }
    public enum eventStatus {
        ACTIVE,
        INACTIVE
    }
}
