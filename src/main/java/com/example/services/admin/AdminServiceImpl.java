package com.example.services.admin;

import com.example.entities.User;
import com.example.enums.UserRole;
import com.example.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@PostConstruct
    public void createAdminAccount(){
      User adminAccount = userRepository.findByRole(UserRole.ADMIN);
     if(adminAccount == null) {
            User admin = new User();
            admin.setMail("admin@test4.com");
            admin.setImie("Test2");
            admin.setNazwisko("Test3");
            admin.setRole(UserRole.ADMIN);
            admin.setHaslo(new BCryptPasswordEncoder().encode("admin2"));
            admin.setRokst(4);
            admin.setKierunek("Test2");
            admin.setGrupa(27);
            admin.setPlec("Mężczyzna");
            userRepository.save(admin);
        }
    }
}
