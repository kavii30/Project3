package com.telusko.studentmanagement2.repository;

import com.telusko.studentmanagement2.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EnrollmentRepository extends JpaRepository <Enrollment,Long>{
    boolean existsByStudentIdAndCourseId(Long studentId,Long courseId);

    @Query("""
select count(distinct e.student.id) from
Enrollment e 
Where e.enrolledDate between :startDate and :endDate

""")
    Long countDistinctStudentByEnrollDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
