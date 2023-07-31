package com.example.asadbeksgroup.Controller;
import com.example.asadbeksgroup.DTO.UserDTO;
import com.example.asadbeksgroup.Payload.LoginReq;
import com.example.asadbeksgroup.Security.JwtServices;
import com.example.asadbeksgroup.Service.AuthService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    private final JwtServices jwtServices;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserDTO dto) {

        System.out.println("ishlayoptsi");
        return service.login(dto);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody LoginReq dto) {
        return service.register(dto);
    }

    @PostMapping("/refresh")
    public HttpEntity<?> refreshUser(@RequestParam String refreshToken) {
        return service.refreshToken(refreshToken);
    }


    ;;
}
