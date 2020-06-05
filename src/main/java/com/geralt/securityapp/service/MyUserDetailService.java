package com.geralt.securityapp.service;

import com.geralt.securityapp.repository.UserRepository;
import com.geralt.securityapp.model.MyUserDetail;
import com.geralt.securityapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return user.map(MyUserDetail::new).get();
    }

    public User getOne(int id) {
        return userRepository.getOne(id);
    }

    public User findByUserName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
