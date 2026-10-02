package com.telusko.studentmanagement2.service.impl;

import com.telusko.studentmanagement2.controller.CourseController;
import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.model.Courses;
import com.telusko.studentmanagement2.repository.CourseRepository;
import com.telusko.studentmanagement2.service.CourseService;
import org.hibernate.annotations.SecondaryRow;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.modelmapper.Converters.Collection.map;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private static final Logger log= LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        log.info("creating course with code: {} .",courseDTO.getCourseCode());

        Courses courses = mapper.map(courseDTO, Courses.class);
        courseRepository.save(courses);

        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCode(String code) {
        log.info("checking if course exists : {} .", code);
        return courseRepository.existsBycourseCodeIgnoreCase(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, Long id) {
        return courseRepository.existsBycourseCodeIgnoreCaseAndIdNot(code, id);
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {
        log.info("list of courses from : {} .", page);

       PageRequest pageRequest= PageRequest.of(page, size,Sort.by(Sort.Direction.DESC,"id"));
       return courseRepository.findByactiveTrue(pageRequest)
        .map(course->mapper.map(course,CourseDTO.class));

    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseByID(Long id) {
      Courses course =courseRepository.findById(id)
              .orElseThrow(()-> new RuntimeException("No course found"));
      return mapper.map(course,CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
       Courses course =courseRepository.findById(id)
               .orElseThrow(()->new RuntimeException("NO course found"));
       mapper.map(courseDTO,course);
       Courses updated=courseRepository.save(course);

        return mapper.map(updated,CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getAllCourse() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream()
                .map(course -> mapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());

    }
}
