package com.example.services.jwt;

import com.example.entities.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
            User user = userRepository.findFirstByMail(mail);
            if (user == null) {
                throw new UsernameNotFoundException("Nie odnalezionio uzytkownika", null);
            }
            return  new org.springframework.security.core.userdetails.User(user.getMail(),user.getHaslo(), new ArrayList<>() );

        }
    }
