package com.geralt.securityapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionid;

    @ManyToOne(cascade = { CascadeType.ALL })
    private User user;


    private Timestamp timestamp;

    @ManyToOne(cascade = { CascadeType.ALL })
    private Product product;
}
