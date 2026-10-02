package com.telusko.studentmanagement2.service;

import com.telusko.studentmanagement2.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code,Long id);

    Page<CourseDTO> getCourses(int page, int size);

    CourseDTO getCourseByID(Long id);

    CourseDTO updateCourse(Long id ,CourseDTO courseDTO);

    List<CourseDTO> getAllCourse();


}
