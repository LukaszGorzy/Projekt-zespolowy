package com.example.services.admin.jwt;

import com.example.entities.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
            Optional<User> userOptional = userRepository.findFirstByMail(mail);
            if (userOptional.isEmpty()) throw new UsernameNotFoundException("Nie odnalezionio uzytkownika", null);
            return  new org.springframework.security.core.userdetails.User(userOptional.get().getMail(),userOptional.get().getHaslo(), new ArrayList<>() );

        }
    }
