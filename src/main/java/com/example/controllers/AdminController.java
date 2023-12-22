package com.example.controllers;

import com.example.dto.SingleStudentDto;
import com.example.dto.StudentDto;
import com.example.services.admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudentDto =  adminService.postStudent(studentDto);
        if (createdStudentDto == null)
            return new ResponseEntity<>("Somthing went wrong.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudentDto);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
       List<StudentDto> allStudents = adminService.getAllStudents();
       return ResponseEntity.ok(allStudents);
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        adminService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable Long studentId) {
        SingleStudentDto singleStudentDto = adminService.getStudentById(studentId);
        if (singleStudentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singleStudentDto);
    }

    @PutMapping("/student/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @RequestBody StudentDto studentDto) {
        StudentDto createdStudentDto =  adminService.updateStudent(studentId, studentDto);
        if (createdStudentDto == null)
            return new ResponseEntity<>("Somthing went wrong.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudentDto);
    }

}