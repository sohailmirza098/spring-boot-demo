package com.geralt.securityapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int productId;

//    @Column(nullable = false)
    @NotNull(message = "is required")
    @Size(min=1, message = "is required")
    String productName;

//    @NotNull(message = "is required")
//    @Size(min=1, message = "is required")
//    String productDescription;

    @NotNull(message = "is required")
    @Size(min=1, message = "is required")
    @Lob
    private String productDescription;

//    @Column(nullable = false)
    @NotNull(message = "is required")
    @Min(value = 0, message = "stock cannot be negative")
//    @Pattern(regexp = "^[0-9]*$", message = "Please enter Number")
    Integer stock;

    @NotNull(message = "is required")
    @Min(value = 0, message = "price cannot be negative")
    Double price;


    @OneToMany(mappedBy = "product", cascade = { CascadeType.ALL })
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

}
