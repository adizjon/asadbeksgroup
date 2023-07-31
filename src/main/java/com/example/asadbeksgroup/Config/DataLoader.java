package com.example.asadbeksgroup.Config;


import com.example.asadbeksgroup.Entity.Company;
import com.example.asadbeksgroup.Entity.Role;
import com.example.asadbeksgroup.Entity.User;
import com.example.asadbeksgroup.Repository.CompanyRepo;
import com.example.asadbeksgroup.Repository.RoleRepo;
import com.example.asadbeksgroup.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final CompanyRepo companyRepo;

    @Override
    public void run(String... args) throws Exception {
        List<Role> all =  roleRepo.findAll();
        if (all.size() == 0) {
            List<Role> tempRoles = new ArrayList<>();
            tempRoles.add(new Role("ROLE_SUPER_ADMIN"));
            List<Role> roles = roleRepo.saveAll(tempRoles);

            User user = new User(
                    "asadbek",
                    "948668666",
                    encoder.encode("123"),
                    roles
            );
            userRepo.save(user);
            Company company = new Company("buxoro", "shiftacademy", "asadbek", "948668666", "adizjonovasadbek906@gmail.com", "buxoro kidoblar olami yonida");
            companyRepo.save(company);
        }


    }
}