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
          admin.setImie("Test3");
          admin.setNazwisko("Test4");
          admin.setMail("admin@test3.com");
          admin.setHaslo(new BCryptPasswordEncoder().encode("admin5"));
          admin.setRokst(4);
          admin.setKierunek("Test6");
          admin.setGrupa(27);
          admin.setPlec("Mężczyzna");
          admin.setRole(UserRole.ADMIN);
          userRepository.save(admin);
        }
    }
}
