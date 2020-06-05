package com.geralt.securityapp.service;

import com.geralt.securityapp.exception.NotFoundException;
import com.geralt.securityapp.model.Product;
import com.geralt.securityapp.model.User;
import com.geralt.securityapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MyUserDetailService userService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void delete(int productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void decrementStock(Product product) {
        product.setStock(product.getStock() - 1);
        productRepository.save(product);
    }

    @Override
    public Product getOne(int productId) {
        return productRepository.getOne(productId);
    }

    @Override
    public List<Object[]> getProducts() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByUserName(username);
        List<Object[]> productList = productRepository.getProducts(user.getId());
        return productList;
    }
}
