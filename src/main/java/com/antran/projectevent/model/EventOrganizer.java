package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_organizers")
public class EventOrganizer {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "eventOrganizer")
    private List<Event> events;

    private String username;
    private String password;
    private String mainEmail;
    private String organizerName;
    private String organizerMainAddress;
    private String mainPhoneNumber;
    private String organizerDescription;
    private String organizerSocialMedia;
    private String organizerLogo;

    private organizerStatus organizerStatus;

    public enum organizerStatus {
        ACTIVE,
        INACTIVE
    }
}
