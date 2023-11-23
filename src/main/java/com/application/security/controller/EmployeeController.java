package com.application.security.controller;

import com.application.security.FileUpload.FileResponse;
import com.application.security.FileUpload.FileServices;
import com.application.security.dto.EmployeeDTO;
import com.application.security.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController  {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FileServices fileServices;

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

    @PostMapping("/uploadprofilepic/{userId}")
    public ResponseEntity<FileResponse> ProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable int userId){
        String imgName=Integer.toString(userId)+".jpg";
        String uploadDir="profilePic";
        return fileServices.uploadFile(file,imgName,"profilePic",uploadDir);
    }

    @GetMapping("/profilePic/{userId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int userId, HttpServletRequest request){
        String fileName=Integer.toString(userId)+".jpg";
        String fileDir="profilePic";
        return fileServices.LoadFile(fileName,fileDir,request);
    }
}
