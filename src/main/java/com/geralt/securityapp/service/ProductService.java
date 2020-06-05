package com.geralt.securityapp.service;

import com.geralt.securityapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    void save(Product product);
    Optional<Product> findById(int productId);
    void delete(int productId);
    void decrementStock(Product product);
    Product getOne(int productId);
    List<Object[]> getProducts();

}
