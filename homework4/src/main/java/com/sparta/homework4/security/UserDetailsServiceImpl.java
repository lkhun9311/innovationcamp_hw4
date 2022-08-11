package com.sparta.homework4.security;

import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;



    public UserDetailsServiceImpl() {
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User)this.userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("Can't find " + username);
        });
        return new UserDetailsImpl(user);
    }
}

