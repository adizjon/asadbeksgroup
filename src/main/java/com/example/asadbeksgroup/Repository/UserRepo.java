package com.example.asadbeksgroup.Repository;

import com.example.asadbeksgroup.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByPhone(String phone);
//    Optional<Object> find(String fromName,String );
}
