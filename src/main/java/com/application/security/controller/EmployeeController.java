package com.application.security.controller;

import com.application.security.dto.EmployeeDTO;
import com.application.security.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add-employee")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeData){
        return employeeService.addEmployee(employeeData);
    }

    @GetMapping("/get-all-empolyees")
    public ResponseEntity<?> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get-all-empolyee-by-employeeId/{employeeId}")
    public ResponseEntity<?> getEmployeeByEmployeeId(@PathVariable int employeeId){
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    @PutMapping("/update-employee-employee")
    public ResponseEntity<?> updateEmployeeById(@RequestBody EmployeeDTO employeeData){
        return employeeService.updateEmployeeById(employeeData);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId, @AuthenticationPrincipal UserDetails userDetails){
        return employeeService.deleteEmployee(employeeId, userDetails);
    }
}
