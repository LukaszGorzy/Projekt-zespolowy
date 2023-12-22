package com.example.repositories;

import com.example.dto.StudentDto;
import com.example.entities.User;
import com.example.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByRole(UserRole userRole);

    User findFirstByMail(String mail);

    List<User> findAllByRole(UserRole userRole);
}
