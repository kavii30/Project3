package com.telusko.studentmanagement2.repository;

import com.telusko.studentmanagement2.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students,Long>{

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

    boolean existsByEmailIgnoreCaseAndIdNot(String email,Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber,Long id);

    Page<Students> findByactiveTrue(Pageable pageable);

    List<Students> findByActiveTrue(Sort sort);

    //@EntityGraph(attributePaths = {"enrollments", "enrollments.courses"})
    @Query(value="""
    select distinct s
    from Students s 
    join s.enrollments e
    """,
    countQuery = """ 
    select count(distinct s)
    from Students s
    join s.enrollments e
    """)
    Page<Students> findByEnrolledStudents(Pageable pageable);

    @Query("""
    select s
    from Students s 
    join fetch s.enrollments e   
    join fetch e.course c 
        where s.id=:id  
    """)
    Optional<Students> findEnrolledStudentDetails(@Param("id") Long id);
    }
