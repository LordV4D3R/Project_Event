package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    private @Id @GeneratedValue UUID id;

    @OneToMany
    private List<Feedback> feedback;

    @OneToMany
    private List<Ticket> ticket;

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
