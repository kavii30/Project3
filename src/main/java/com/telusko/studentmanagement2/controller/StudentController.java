package com.telusko.studentmanagement2.controller;

import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.dto.StudentDTO;
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
@RequestMapping("/students")
public class StudentController {

    private static final Logger log= LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @GetMapping("/new")
    public String showCreateStudents(Model model){
        log.info("GET/new- showing create Students page ");
        model.addAttribute("studentDTO",new StudentDTO());
        model.addAttribute("currentPage", "students");
        return "add-student";
    }

    @GetMapping("/list")
    public String listStudents(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue="5") int size,
                               Model model,
                               @RequestParam(value="message",required=false) String message){
        log.info("GET/list - showing list Students page ");
       Page<StudentDTO> students=studentService.getstudents( page, size);
       model.addAttribute("students",students);
        model.addAttribute("currentPage", "students");
        model.addAttribute("message",message);


        return "students";
    }
    @PostMapping("/save")
    public String createStudents(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        log.info("post/save - create student request received");
        if(bindingResult.hasErrors()){
            return "add-student";
        }
        if(studentService.existsByEmail(studentDTO.getEmail())){
            log.error("post/save - email must be unique");
            bindingResult.rejectValue("email","error.email.exists","email must be unique");
            return "add-student";
        }
        if(studentService.existsByPhoneNumber(studentDTO.getPhoneNumber())){
            log.error("post/save - PhoneNumber must be unique");
            bindingResult.rejectValue("phoneNumber","phoneNumber.already.exists","phoneNumber must be unique");
            return "add-student";
        }
        studentService.createStudent(studentDTO);
        redirectAttributes.addAttribute("message","student is added successfully!!");
        return "redirect:/students/list";
    }
    @GetMapping("/{id}")
    public String getStudentByid(@PathVariable Long id,Model model){
       StudentDTO studentDTO =studentService.getStudentInfoById(id);
       model.addAttribute("student",studentDTO);
        model.addAttribute("currentPage", "students");
        return "view-student";
    }
    @GetMapping("{id}/edit")
    public String editStudentForm(@PathVariable Long id,Model model){
        StudentDTO student=studentService.getStudentInfoById(id);
        model.addAttribute("studentDTO",student);
        model.addAttribute("currentPage", "students");
        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudentDetails(@PathVariable Long id,
                                       @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        log.info("post/{}/update - update request received", id);
        studentDTO.setId(id);

        if (bindingResult.hasErrors()) {
            log.error("post/{}/update - validation error", id);
            return "edit-student";
        }
        if (studentService.existsByEmailIgnoreCaseAndIdNot(studentDTO.getEmail(), id)) {
            log.error("post/{}/update - email must be unique", id);
            bindingResult.rejectValue("email", "error.email.exists", "Enter another email address");
            return "edit-student";
        }
        if (studentService.existsByPhoneNumberAndIdNot(studentDTO.getPhoneNumber(), id)) {
            log.error("post/{}/update - phone number must be unique", id);
            bindingResult.rejectValue("phoneNumber", "error.phoneNumber.exists", "Enter another phone number");
            return "edit-student";
        }
        studentService.updateStudents(id, studentDTO);
        redirectAttributes.addAttribute("message", "student data updated successfully");
        return "redirect:/students/list";
    }

    }



