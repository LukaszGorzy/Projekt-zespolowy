package com.example.services.student;

import com.example.dto.SingleStudentDto;
import com.example.dto.StudentDto;
import com.example.dto.StudentLeaveDto;
import com.example.entities.StudentLeave;
import com.example.entities.User;
import com.example.enums.StudentLeaveStatus;
import com.example.repositories.StudentLeaveRepository;
import com.example.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;

    private final StudentLeaveRepository studentLeaveRepository;

    @Override
    public SingleStudentDto getStudentById(Long studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        SingleStudentDto singleStudentDto = new SingleStudentDto();
        optionalUser.ifPresent(user -> singleStudentDto.setStudentDto(user.getStudentDto()));
        return singleStudentDto;
    }

    @Override
    public StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto) {
        Optional<User> optionalUser = userRepository.findById(studentLeaveDto.getUserId());
        if (optionalUser.isPresent()) {
            StudentLeave studentLeave = new StudentLeave();
            studentLeave.setSubject(studentLeaveDto.getSubject());
            studentLeave.setBody(studentLeaveDto.getBody());
            studentLeave.setDate(new Date());
            studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Pending);
            studentLeave.setUser(optionalUser.get());
            StudentLeave SubmittedStudentLeave = studentLeaveRepository.save(studentLeave);
            StudentLeaveDto studentLeaveDto1 = new StudentLeaveDto();
            studentLeaveDto1.setId(SubmittedStudentLeave.getId());
            return studentLeaveDto1;
        }
        return null;
    }

    @Override
    public List<StudentLeaveDto> getAllAppliedLeavesByStudentId(Long studentId) {
        return studentLeaveRepository.findAllByUserId(studentId).stream().map(StudentLeave::getStudentLeaveDto).collect(Collectors.toList());
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
