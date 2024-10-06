package com.antran.projectevent.dto;

import com.antran.projectevent.constant.enums.AccountRole;
import lombok.Data;

@Data
public class TokenData {
    private AccountRole accountRole;
    private String username;
    private String fullName;

}
