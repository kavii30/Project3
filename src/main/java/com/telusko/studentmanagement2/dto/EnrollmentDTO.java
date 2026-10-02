package com.telusko.studentmanagement2.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentDTO {

    @NotNull(message ="student is required ")
    private Long studentId;

    @NotNull(message ="select at least one course ")
    private List<Long> courseIds=new ArrayList<>();

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
