package com.example.asadbeksgroup.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String tin;
    private String companyname;
    private String longe;
    private String lat;
    @ManyToOne
//    ???
    private CustomerCategory customerCategory;
    @ManyToOne
    private Teretory teretory;
//    ???
}
