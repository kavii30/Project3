package com.telusko.studentmanagement2.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class StudentDTO {

    private Long id;

    @NotBlank(message="First Name is required ")
    @Size(max=50, message="LastName must be within 50 characters ")
    private String firstName;

    @NotBlank(message="Last Name is required ")
    @Size(max=50, message="LastName must be within 50 characters ")
    private String lastName;

    @NotBlank(message="email is required ")
    private String email;

    @NotBlank(message="phone number is required ")
    @Pattern(regexp="^[6-9]\\d{9}$", message="Enter a valid phone number ")
    private String phoneNumber;

    @Size(max=500, message="Address must be within 500 characters ")
    private String Address;


    private boolean active;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
