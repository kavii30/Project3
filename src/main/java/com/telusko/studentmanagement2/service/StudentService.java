package com.telusko.studentmanagement2.service;

import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.dto.StudentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

boolean existsByEmail(String email);
boolean existsByPhoneNumber(String phoneNumber);

StudentDTO createStudent(StudentDTO studentDTO);

Page<StudentDTO> getstudents(int page,int size);

StudentDTO getStudentInfoById(Long id);

StudentDTO updateStudents(Long id,StudentDTO studentDTO);

boolean existsByEmailIgnoreCaseAndIdNot(String email,Long id);

boolean existsByPhoneNumberAndIdNot(String phoneNumber,Long id);

     List<StudentDTO> getAllStudents();



}
