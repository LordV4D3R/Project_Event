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

    public Account(String username, String password, String mainEmail, String fullName, String mainPhoneNumber, LocalDate birth,
                   sex sex, String mainAddress, systemRole systemRole, accountStatus accountStatus, List<Feedback> feedbacks, List<Order> orders) {
        this.username = username;
        this.password = password;
        this.mainEmail = mainEmail;
        this.fullName = fullName;
        this.mainPhoneNumber = mainPhoneNumber;
        this.birthDate = birth;
        this.sex = sex;
        this.mainAddress = mainAddress;
        this.systemRole = systemRole;
        this.accountStatus = accountStatus;
        this.feedbacks = feedbacks;
        this.orders = orders;
    }

}
