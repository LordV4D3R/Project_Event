package com.antran.projectevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
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
    private LocalDate birthDate;
    private sex sex;
    private String mainAddress;

    private systemRole systemRole;
    private accountStatus accountStatus;

    public enum systemRole {
        ADMIN,
        MEMBER
    }
    public enum accountStatus {
        ACTIVE,
        INACTIVE
    }
    public enum sex {
        MALE,
        FEMALE,
        OTHER
    }

}
