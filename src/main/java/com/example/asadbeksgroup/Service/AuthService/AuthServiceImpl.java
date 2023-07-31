package com.example.asadbeksgroup.Service.AuthService;

import com.example.asadbeksgroup.DTO.UserDTO;
import com.example.asadbeksgroup.Entity.Role;
import com.example.asadbeksgroup.Entity.User;
import com.example.asadbeksgroup.Payload.LoginReq;
import com.example.asadbeksgroup.Repository.RoleRepo;
import com.example.asadbeksgroup.Repository.UserRepo;
import com.example.asadbeksgroup.Security.JwtServices;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepo usersRepository;
    private final RoleRepo roleRepo;
    private final JwtServices jwtServices;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public HttpEntity<?> register(LoginReq dto) {
        List<Role> roles = new ArrayList<>();
        List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
        if (roleUser == null) {
            roles.add(roleRepo.save(new Role(0, "ROLE_USER")));
        } else {
            roles.add(roleUser.get(0));
        }
        User user = new User(
                null,
                dto.getUsername(),
                dto.getPassword(),
                roles
        );
        usersRepository.save(user);


        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                dto.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationConfiguration.getAuthenticationManager().authenticate(authenticationToken);

        String token = Jwts
                .builder()
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(jwtServices.getSigningKey())
                .compact();
        return ResponseEntity.ok(token);
    }

    @Override
    public HttpEntity<?> login(UserDTO dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok("BAD_CREDENTIALS");
        }
        User users = usersRepository.findByPhone(dto.getPhone()).orElseThrow(() -> new RuntimeException("Cannot find User With Id:" + dto.getPhone()));
        List<Role> roles = roleRepo.findAll();
        String access_token = jwtServices.generateJwtToken(users);
        String refresh_token = jwtServices.generateJwtRefreshToken(users);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("refresh_token", refresh_token);
        return returningProcess(dto, access_token, map);
    }

    private ResponseEntity<String> getStringResponseEntity(UserDTO dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok("BAD_CREDENTIALS");
        }
        ;
        return null;
    }

    private static ResponseEntity<?> returningProcess(UserDTO dto, String access_token, Map<String, Object> map) {
        if (dto.isRememberMe()){
            return ResponseEntity.ok(map);
        }else {
            return ResponseEntity.ok(access_token);
        }
    }

    @Override
    public HttpEntity<?> refreshToken(String refreshToken) {
        String id = jwtServices.extractSubjectFromJwt(refreshToken);
        User users = usersRepository.findById(UUID.fromString(id)).orElseThrow();
        String access_token = jwtServices.generateJwtToken(users);
        return ResponseEntity.ok(access_token);
    }

    @Override
    public HttpEntity<?> decode(String token) {
        boolean isExpired = jwtServices.validateToken(token);
        User user = null;
        if (isExpired) {
            String userId = jwtServices.extractSubjectFromJwt(token);
            user = usersRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("Cannot find User With Id:" + userId));
        }
        return ResponseEntity.ok(user);
    }
}
