package com.geralt.securityapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean active = true;
    private String roles = "ROLE_USER";

    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
    private List<Transaction> transactions = new ArrayList<>();

}
