package com.telusko.studentmanagement2.dto;

public class DashboardStatsDTO {

    private Long totalStudents;
    private Long totalCourses;
    private String topPerformingCourse;
    private Long studentsEnrolledThisMonth;

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public String getTopPerformingCourse() {
        return topPerformingCourse;
    }

    public void setTopPerformingCourse(String topPerformingCourse) {
        this.topPerformingCourse = topPerformingCourse;
    }

    public Long getStudentsEnrolledThisMonth() {
        return studentsEnrolledThisMonth;
    }

    public void setStudentsEnrolledThisMonth(Long studentsEnrolledThisMonth) {
        this.studentsEnrolledThisMonth = studentsEnrolledThisMonth;
    }
}
