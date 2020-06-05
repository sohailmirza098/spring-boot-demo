package com.geralt.securityapp.controller;

import com.geralt.securityapp.model.User;
import com.geralt.securityapp.repository.ProductRepository;
import com.geralt.securityapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public ModelAndView home() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping("/save")
    public ModelAndView saveUser(User user) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return modelAndView;
    }


}
