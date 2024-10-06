package com.antran.projectevent.model;

import com.antran.projectevent.constant.enums.AccountRole;
import com.antran.projectevent.constant.enums.AccountStatus;
import com.antran.projectevent.constant.enums.Sex;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String mainAddress;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;


}
