package com.example.services.admin;

import com.example.dto.SingleStudentDto;
import com.example.dto.StudentDto;
import com.example.entities.User;
import com.example.enums.UserRole;
import com.example.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

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
          admin.setRole(UserRole.ADMIN);
          userRepository.save(admin);
        }
    }

    @Override
    public StudentDto postStudent(StudentDto studentDto) {
        User userOptional = userRepository.findFirstByMail(studentDto.getMail());
        if (userOptional == null) {
            User user = new User();
            BeanUtils.copyProperties(studentDto, user);
            user.setHaslo(new BCryptPasswordEncoder().encode(studentDto.getHaslo()));
            user.setRole(UserRole.STUDENT);
            User createdUser = userRepository.save(user);
            StudentDto createdStudentDto = new StudentDto();
            createdStudentDto.setId(createdUser.getId());
            createdStudentDto.setMail(createdUser.getMail());
            return createdStudentDto;
        }
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long studentId) {
        userRepository.deleteById(studentId);
    }

    @Override
    public SingleStudentDto getStudentById(Long studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
            SingleStudentDto singleStudentDto = new SingleStudentDto();
            optionalUser.ifPresent(user -> singleStudentDto.setStudentDto(user.getStudentDto()));
            return singleStudentDto;
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setMail(studentDto.getMail());
            user.setImie(studentDto.getImie());
            user.setNazwisko(studentDto.getNazwisko());
            user.setKierunek(studentDto.getKierunek());
            user.setRokst(studentDto.getRokst());
            user.setPlec(studentDto.getPlec());
            User updatedStudent = userRepository.save(user);
            StudentDto updatedStudentDto = new StudentDto();
            updatedStudentDto.setId(updatedStudent.getId());
            return updatedStudentDto;
        }
        return null;
    }

}
