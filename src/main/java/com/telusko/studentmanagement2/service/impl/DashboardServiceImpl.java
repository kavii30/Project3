package com.telusko.studentmanagement2.service.impl;

import com.telusko.studentmanagement2.dto.DashboardStatsDTO;
import com.telusko.studentmanagement2.repository.CourseRepository;
import com.telusko.studentmanagement2.repository.EnrollmentRepository;
import com.telusko.studentmanagement2.repository.StudentRepository;
import com.telusko.studentmanagement2.service.DashboardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public DashboardServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository, ModelMapper mapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        Long totalStudents=studentRepository.count();
        Long totalCourses=courseRepository.count();
        String topPerformingCourse = getTopPerformingCourse();
        YearMonth currentmonth=YearMonth.now();
        LocalDateTime startDate=currentmonth.atDay(1).atStartOfDay();
        LocalDateTime lastDate=currentmonth.atEndOfMonth().atTime(LocalTime.MAX);
        Long studentEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate,lastDate);

        DashboardStatsDTO dashboardStatsDTO=new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourses);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);
        return dashboardStatsDTO;
    }
    private String getTopPerformingCourse(){

        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e->e.getCourse().getCourseName(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
