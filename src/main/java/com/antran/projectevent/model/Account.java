package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "account")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;

    private String username;
    private String password;
    private String mainEmail;
    private String fullName;
    private String mainPhoneNumber;
    private String birthDate;
    private String sex;
    private String mainAddress;

    private systemRole systemRole;
    private accountStatus accountStatus;

    public enum systemRole {
        ADMIN,
        MEMBER,
        CUSTOMER,
        EVENT_ORGANIZER
    }
    public enum accountStatus {
        ACTIVE,
        INACTIVE
    }

}
