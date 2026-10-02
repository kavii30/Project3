package com.telusko.studentmanagement2.service.impl;

import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.dto.EnrollmentDTO;
import com.telusko.studentmanagement2.dto.EnrollmentSummaryDTO;
import com.telusko.studentmanagement2.dto.StudentDTO;
import com.telusko.studentmanagement2.model.Courses;
import com.telusko.studentmanagement2.model.Enrollment;
import com.telusko.studentmanagement2.model.Students;
import com.telusko.studentmanagement2.repository.CourseRepository;
import com.telusko.studentmanagement2.repository.EnrollmentRepository;
import com.telusko.studentmanagement2.repository.StudentRepository;
import com.telusko.studentmanagement2.service.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class EnrollmentImpl implements EnrollmentService {

    private static final Logger log= LoggerFactory.getLogger(EnrollmentImpl.class);


    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;



    public EnrollmentImpl(EnrollmentRepository enrollmentRepository,CourseRepository courseRepository,StudentRepository studentRepository,ModelMapper mapper){
        this.enrollmentRepository=enrollmentRepository;
        this.studentRepository=studentRepository;
        this.courseRepository=courseRepository;
        this.mapper=mapper;
    }


    @Override
    public void enrollStudentToCourses(EnrollmentDTO enrollmentDTO) {
        log.info("request from enrollStudent to course");

        Students student=studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(()->new RuntimeException("student Id is invalid"));

        for(Long courseId :enrollmentDTO.getCourseIds()){
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(()->new RuntimeException("invalid Course id "));

            if(enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(),courseId)){
                continue;
            }
            Enrollment enrollment=new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            student.getEnrollments().add(enrollment);
            course.getEnrollments().add(enrollment);

            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {

            log.info("list of enrolled students from : {} " ,page);

            PageRequest pageRequest= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));
            return studentRepository.findByEnrolledStudents(pageRequest)
                    .map(student ->{
                        EnrollmentSummaryDTO dto=new EnrollmentSummaryDTO();
                        dto.setStudentId(student.getId());
                        dto.setStudentName(student.getFirstName() + " "+student.getLastName());
                        dto.setEmail(student.getEmail());
                        dto.setCourseCount(student.getEnrollments().size());
                        BigDecimal totalFee=student.getEnrollments().stream()
                                .map(enrollment -> enrollment.getCourse().getFee())
                                .filter(fee->fee!=null)
                                .reduce(BigDecimal.ZERO,BigDecimal::add);
                        dto.setTotalFee(totalFee);
                        return dto;
                    });
    }

    @Override
    public EnrollmentSummaryDTO findEnrollmentSummaryCourseDetails(Long studentId) {



        return studentRepository.findEnrolledStudentDetails(studentId)
                .map(student ->{
                    EnrollmentSummaryDTO dto=new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " "+student.getLastName());
                    dto.setEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee=student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee->fee!=null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDTO> courseList= student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course ->mapper.map(course, CourseDTO.class))
                            .collect(Collectors.toList());
                    dto.setCourseList(courseList);
                    return dto;
                })
                .orElseThrow(()->new RuntimeException("student nor found"));

    }

    @Override
    public List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents(int page, int size) {

        log.info("list of recentle enrolled students ");

        PageRequest pageRequest=PageRequest.of(0,5,Sort.by(Sort.Direction.DESC,"id"));
        return studentRepository.findByEnrolledStudents(pageRequest)
                .map(student ->{
                    EnrollmentSummaryDTO dto=new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " "+student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee=student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee->fee!=null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                })
                .getContent();
    }

    @Override
    public List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents() {

        log.info("list of recentle enrolled students ");

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        return studentRepository.findByEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                })
                .getContent();
    }
}
