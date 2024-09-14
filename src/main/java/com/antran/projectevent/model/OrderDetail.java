package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    private @Id @GeneratedValue UUID id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Date createOn;
    private int quantity;
    private double price;

    private orderStatus orderStatus;

    public enum orderStatus {
        ACTIVE,
        INACTIVE
    }

}
