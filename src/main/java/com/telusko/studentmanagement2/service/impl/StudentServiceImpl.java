package com.telusko.studentmanagement2.service.impl;

import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.dto.StudentDTO;
import com.telusko.studentmanagement2.model.Students;
import com.telusko.studentmanagement2.repository.StudentRepository;
import com.telusko.studentmanagement2.service.StudentService;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger log= LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository,ModelMapper mapper){
        this.studentRepository=studentRepository;
        this.mapper=mapper;
    }

    @Override

    public boolean existsByEmail(String email) {
        log.info("email from create student");
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        log.info("phoneNumber from create student");
        return studentRepository.existsByPhoneNumberIgnoreCase(phoneNumber);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("saving student data");
       Students students= mapper.map(studentDTO,Students.class);
        Students saved=studentRepository.save(students);
        StudentDTO stud= mapper.map(saved,StudentDTO.class);
        return stud;

    }

    @Override
    public Page<StudentDTO> getstudents(int page, int size) {

        log.info("list of students from the page : {} " ,page);
        PageRequest pageRequest= PageRequest.of(page, size,Sort.by(Sort.Direction.DESC,"id"));
        return studentRepository.findByactiveTrue(pageRequest)
                .map(students ->mapper.map(students, StudentDTO.class));
    }

    @Override
    public StudentDTO getStudentInfoById(Long id) {
      Students students = studentRepository.findById(id)
              .orElseThrow(()->new RuntimeException("No Student Found"));
      return mapper.map(students,StudentDTO.class);

    }

    @Override
    public StudentDTO updateStudents(Long id, StudentDTO studentDTO) {
        Students students =studentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No student found"));
        mapper.map(studentDTO,students);
        Students update=studentRepository.save(students);
        return mapper.map(update,StudentDTO.class);

    }

    @Override
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email,id);
    }

    @Override
    public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id) {
        return studentRepository.existsByPhoneNumberAndIdNot(phoneNumber,id);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findByActiveTrue(Sort.by("id")).stream()
                .map(student -> mapper.map(student,StudentDTO.class))
                .collect(Collectors.toList());

    }

}