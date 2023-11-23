package com.application.security.service;

import com.application.security.dto.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeService {
    ResponseEntity<?> addEmployee(EmployeeDTO employeeData);

    ResponseEntity<?> getAllEmployees();

    ResponseEntity<?> getEmployeeByEmployeeId(int employeeId);

    ResponseEntity<?> updateEmployeeById(EmployeeDTO employeeData);

    ResponseEntity<?> deleteEmployee(int employeeId, UserDetails userDetails);
}
