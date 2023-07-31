package com.example.asadbeksgroup.Service.AuthService;

import com.example.asadbeksgroup.DTO.UserDTO;
import com.example.asadbeksgroup.Payload.LoginReq;
import org.springframework.http.HttpEntity;

public interface AuthService {
    HttpEntity<?> register(LoginReq dto);
    HttpEntity<?> login(UserDTO dto);
    HttpEntity<?> refreshToken(String refreshToken);
    HttpEntity<?> decode(String token);
}
