package com.antran.projectevent.model;

import com.antran.projectevent.constant.enums.AccountRole;
import com.antran.projectevent.constant.enums.AccountStatus;
import com.antran.projectevent.constant.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements UserDetails {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "account")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String mainEmail;
    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Full name should contain only letters")
    private String fullName;
    @Pattern(regexp = "(\\+84|0)\\d{9,10}", message = "Phone number should be valid")
    private String mainPhoneNumber;
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "Date should be in format yyyy-MM-dd")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String mainAddress;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
