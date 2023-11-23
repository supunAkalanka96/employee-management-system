package com.application.security.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {
    private int employeeId;
    private String jobTitle;
    private double salary;
    private Date dob;
    private int userId;
}
