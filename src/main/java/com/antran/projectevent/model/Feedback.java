package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {
    private @Id @GeneratedValue UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String content;
    private int starRating;
    private Date createOn;

    private feedbackStatus feedbackStatus;
    public enum feedbackStatus {
        ACTIVE,
        INACTIVE
    }




}
