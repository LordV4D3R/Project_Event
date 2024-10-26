package com.antran.projectevent.service.interfaceservice;

import com.antran.projectevent.constant.common.BusinessResult;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.model.dto.LoginRequest;
import com.antran.projectevent.model.dto.RegisterRequest;
import com.antran.projectevent.model.dto.TokenResponse;

public interface IAuthenticationService {
    BusinessResult<TokenResponse> login(LoginRequest loginRequest);
    Account register(RegisterRequest registerRequest);
}
