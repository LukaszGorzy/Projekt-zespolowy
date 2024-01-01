package com.example.entities;

import com.example.dto.StudentLeaveDto;
import com.example.enums.StudentLeaveStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class StudentLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String body;

    private Date date;

    private StudentLeaveStatus studentLeaveStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public StudentLeaveDto getStudentLeaveDto() {
        StudentLeaveDto studentLeaveDto = new StudentLeaveDto();
        studentLeaveDto.setId(id);
        studentLeaveDto.setSubject(subject);
        studentLeaveDto.setBody(body);
        studentLeaveDto.setDate(date);
        studentLeaveDto.setStudentLeaveStatus(studentLeaveStatus);
        studentLeaveDto.setUserId(user.getId());
        return studentLeaveDto;
    }

}
