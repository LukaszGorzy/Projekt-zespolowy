package com.example.services.student;

import com.example.dto.SingleStudentDto;
import com.example.dto.StudentDto;
import com.example.dto.StudentLeaveDto;

import java.util.List;

public interface StudentService {
    SingleStudentDto getStudentById(Long studentId);

    StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto);

    List<StudentLeaveDto> getAllAppliedLeavesByStudentId(Long studentId);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);
}
