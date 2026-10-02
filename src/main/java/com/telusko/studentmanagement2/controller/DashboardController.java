package com.telusko.studentmanagement2.controller;

import com.telusko.studentmanagement2.service.DashboardService;
import com.telusko.studentmanagement2.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

    public DashboardController(EnrollmentService enrollmentService,DashboardService dashboardService) {
        this.dashboardService=dashboardService;

        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("dashboardStats",dashboardService.getDashboardStats());
        model.addAttribute("students",enrollmentService.getRecentlyEnrolledStudents());
        return "dashboard";
    }
}
