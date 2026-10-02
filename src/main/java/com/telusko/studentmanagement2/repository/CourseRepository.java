package com.telusko.studentmanagement2.repository;

import com.telusko.studentmanagement2.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses,Long> {

    boolean existsBycourseCodeIgnoreCase(String code);

    boolean existsBycourseCodeIgnoreCaseAndIdNot(String code,Long id);

      Page<Courses> findByactiveTrue(Pageable pageable);

      List<Courses> findByActiveTrue(Sort sort);
}
