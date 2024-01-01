package com.example.services.admin;

import com.example.dto.*;
import com.example.entities.Fee;
import com.example.entities.StudentLeave;
import com.example.entities.Teacher;
import com.example.entities.User;
import com.example.enums.StudentLeaveStatus;
import com.example.enums.UserRole;
import com.example.repositories.FeeRepository;
import com.example.repositories.StudentLeaveRepository;
import com.example.repositories.TeacherRepository;
import com.example.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final FeeRepository feeRepository;

    private final StudentLeaveRepository studentLeaveRepository;

    private final TeacherRepository teacherRepository;

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

    @Override
    public FeeDto payFee(Long studentId, FeeDto feeDto) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            Fee fee = new Fee();
            fee.setAmount(feeDto.getAmount());
            fee.setMonth(feeDto.getMonth());
            fee.setDescription(feeDto.getDescription());
            fee.setCratedDate(new Date());
            fee.setGivenBy(feeDto.getGivenBy());
            fee.setUser(optionalUser.get());
            Fee paidFee =  feeRepository.save(fee);
            FeeDto paidfeeDto = new FeeDto();
            paidfeeDto.setId(paidFee.getId());
            return paidfeeDto;
        }
        return null;
    }

    @Override
    public List<StudentLeaveDto> getAllAppliedLeaves() {
        return studentLeaveRepository.findAll().stream().map(StudentLeave::getStudentLeaveDto).collect(Collectors.toList());
    }

    @Override
    public StudentLeaveDto changeLeaveStatus(Long leaveId, String status) {
        Optional<StudentLeave> optionalStudentLeave = studentLeaveRepository.findById(leaveId);
        if (optionalStudentLeave.isPresent()) {
            StudentLeave studentLeave = optionalStudentLeave.get();
            if (Objects.equals(status, "Approve")) {
                studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Approved);
            } else {
                studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Disapproved);
            }
            StudentLeave updatedStudentLeave = studentLeaveRepository.save(studentLeave);
            StudentLeaveDto updatedStudentLeaveDto = new StudentLeaveDto();
            updatedStudentLeaveDto.setId(updatedStudentLeave.getId());
            return updatedStudentLeaveDto;
        }
        return null;
    }

    @Override
    public TeacherDto postTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDto, teacher);
        Teacher createdTeacher = teacherRepository.save(teacher);
        TeacherDto createdTeacherDto = new TeacherDto();
        createdTeacherDto.setId(createdTeacher.getId());
        return createdTeacherDto;
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public SingleTeacherDto getTeacherById(Long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            SingleTeacherDto singleTeacherDto = new SingleTeacherDto();
            singleTeacherDto.setTeacherDto(optionalTeacher.get().getTeacherDto());
            return singleTeacherDto;
        }
        return null;
    }

    @Override
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            Teacher updateTeacher = optionalTeacher.get();
            updateTeacher.setName(teacherDto.getName());
            updateTeacher.setGender(teacherDto.getGender());
            updateTeacher.setAddress(teacherDto.getAddress());
            updateTeacher.setDob(teacherDto.getDob());
            updateTeacher.setDepartment(teacherDto.getDepartment());
            updateTeacher.setQualification(teacherDto.getQualification());
            Teacher updatedTeacher = teacherRepository.save(updateTeacher);
            TeacherDto updatedTeacherDto = new TeacherDto();
            updatedTeacherDto.setId(updatedTeacher.getId());
            return updatedTeacherDto;
        }
        return null;
    }
}
