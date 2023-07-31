package com.example.asadbeksgroup.Repository;

import com.example.asadbeksgroup.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
}
