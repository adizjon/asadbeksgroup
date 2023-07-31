package com.example.asadbeksgroup.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String region;
    private String company_name;
    private String name;
    private String supportPhone;
    private String email;
    private String address;

    public Company(String region, String company_name, String name, String supportPhone, String email, String address) {
        this.region = region;
        this.company_name = company_name;
        this.name = name;
        this.supportPhone = supportPhone;
        this.email = email;
        this.address = address;
    }
}
