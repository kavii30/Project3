package com.telusko.studentmanagement2.controller;


import com.telusko.studentmanagement2.dto.EnrollmentDTO;
import com.telusko.studentmanagement2.dto.EnrollmentSummaryDTO;
import com.telusko.studentmanagement2.dto.StudentDTO;
import com.telusko.studentmanagement2.service.CourseService;
import com.telusko.studentmanagement2.service.EnrollmentService;
import com.telusko.studentmanagement2.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private static final Logger log= LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService,StudentService studentService
    ,EnrollmentService enrollmentService){
        this.courseService = courseService;
        this.studentService=studentService;
        this.enrollmentService=enrollmentService;
    }


    @GetMapping("/showEnroll")
    public String showEnroll(Model model) {
        log.info("Get /enrollments/showenroll - showing create course page. ");

        model.addAttribute("enrollmentDTO", new EnrollmentDTO());
        model.addAttribute("courseList",courseService.getAllCourse());
        model.addAttribute("studentList",studentService.getAllStudents());
        model.addAttribute("currentPage", "enrollments");

        return "enroll-course";
    }

    @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue="3") int size,
                                 Model model,
                                 @RequestParam(value="message",required=false) String message){
        log.info("GET/enrollmentList - showing enrolllist Students page ");
        Page<EnrollmentSummaryDTO> students=enrollmentService.getEnrolledStudents( page, size);
        model.addAttribute("students",students);
        model.addAttribute("message",message);
        model.addAttribute("currentPage", "enrollments");


        return "enrolled-students";
    }


    @PostMapping("/enrollCourse")
        public String enrollCourse(@Valid @ModelAttribute("enrollmentDTO") EnrollmentDTO enrollmentDTO, BindingResult bindingResult,
                                   Model model, RedirectAttributes redirectAttributes){
        log.info("Post /enrollments/enrollCourses - enrollment of students into courses . ");

        if(bindingResult.hasErrors()){
            model.addAttribute("courseList",courseService.getAllCourse());
            model.addAttribute("studentList",studentService.getAllStudents());

            return "enroll-course";
        }
        enrollmentService.enrollStudentToCourses(enrollmentDTO);
        redirectAttributes.addAttribute("message","enrollment is successful!! ");
        log.info("post/enrollments/enrollcourses- enrollmentService -succesfully enroll the student ");

        return "redirect:/enrollments/enrollmentList";
    }
    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id,Model model,
    @RequestParam(defaultValue="enrollments") String source){
       EnrollmentSummaryDTO enrollmentSummaryDTO =enrollmentService.findEnrollmentSummaryCourseDetails(id);

       model.addAttribute("enrollmentSummaryDTO",enrollmentSummaryDTO);
       model.addAttribute("source",source);
        model.addAttribute("currentPage", "enrollments");
        return "enrollment-details";

    }
}
