package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {
    private @Id @GeneratedValue UUID id;


    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String ticketName;
    private String ticketType;
    private double price;
    private String ticketDescription;
    private int quantity;
    private Date createOn;

    private Date sellStartOn;
    private Date sellEndOn;

    private isDeleted isDeleted;
    private isSoldOut isSoldOut;
    private ticketStatus ticketStatus;

    public enum isDeleted {
        YES,
        NO
    }

    public enum isSoldOut {
        YES,
        NO
    }
    public enum ticketStatus {
        ACTIVE,
        INACTIVE
    }
}
