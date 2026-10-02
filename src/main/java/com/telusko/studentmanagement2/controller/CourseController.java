package com.telusko.studentmanagement2.controller;


import com.telusko.studentmanagement2.dto.CourseDTO;
import com.telusko.studentmanagement2.exception.GlobalExceptionHandler;
import com.telusko.studentmanagement2.service.CourseService;
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
@RequestMapping("/course")
public class CourseController {
    private static final Logger log= LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController( CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        log.info("Get /course/new - showing create course page. ");
        model.addAttribute("courseDTO", new CourseDTO());
        model.addAttribute("currentPage", "courses");
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourses(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="5")int size
            ,Model model,
    @RequestParam(value="message",required=false)String message) {

        log.info("Get /course/list - showing course list page. ");

        Page<CourseDTO> courses = courseService.getCourses(page,size);
        model.addAttribute("courses",courses);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("message",message);

        return "courses";
    }
    @PostMapping("/list")
    public String CreateCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        log.info("Post /course -  create course request received. ");
        if(bindingResult.hasErrors()){
            log.error("Post /course - page return due to validation error. ");
            return "add-course";
        }
       if(courseService.existsByCode(courseDTO.getCourseCode())){
           log.error("Post /course - code must be unique . ");

           bindingResult.rejectValue("courseCode",null,"error.courseCode.exists");
           return "add-course";
       }


       courseService.createCourse(courseDTO);
       redirectAttributes.addAttribute("message","Course Created successfully");
        log.info("Post /course - create course successfully created. ");
       return "redirect:/course/list";
    }
    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id,Model model){
        CourseDTO course=courseService.getCourseByID(id);
        model.addAttribute("course",course);
        model.addAttribute("currentPage", "courses");
        return "view-course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id,Model model){
        CourseDTO course=courseService.getCourseByID(id);
        model.addAttribute("courseDTO",course);
        model.addAttribute("currentPage", "courses");
        return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                               BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes){
        log.info("post/{id}/update- update request has been received {}",id);
if(bindingResult.hasErrors()){
    log.error("post/{id}/update - page return validation error ");
    return "edit-course";
}
if(courseService.existsByCodeAndIdNot(courseDTO.getCourseCode(),id)){
    log.error("Post /{id}/update - code must be unique . ");

    bindingResult.rejectValue("courseCode",null,"error.courseCode.exists");
    return "edit-course";

}
        courseService.updateCourse(id,courseDTO);
        redirectAttributes.addAttribute("message","Course updated successfully");
        log.info("Post /{id}/update - updated course successfully created. ");

        return "redirect:/course/list";
    }
}

