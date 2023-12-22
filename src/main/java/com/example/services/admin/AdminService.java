package com.example.services.admin;

import com.example.dto.SingleStudentDto;
import com.example.dto.StudentDto;

import java.util.List;

public interface AdminService {

    StudentDto postStudent(StudentDto studentDto);

    List<StudentDto> getAllStudents();

    void deleteStudent(Long studentId);

    SingleStudentDto getStudentById(Long studentId);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);
}
