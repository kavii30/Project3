package com.telusko.studentmanagement2.service;

import com.telusko.studentmanagement2.dto.EnrollmentDTO;
import com.telusko.studentmanagement2.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page,int size);

     EnrollmentSummaryDTO findEnrollmentSummaryCourseDetails(Long studentid);

    List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents (int page,int size);

    public List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();
}
