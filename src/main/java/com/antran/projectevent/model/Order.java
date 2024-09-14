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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Date createOn;
    private Date updateOn;
    private int totalQuantity;
    private double totalPrice;

    private orderStatus orderStatus;

    public enum orderStatus {
        ACTIVE,
        INACTIVE
    }
}
