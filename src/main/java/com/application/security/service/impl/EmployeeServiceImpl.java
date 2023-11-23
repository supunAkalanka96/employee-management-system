package com.application.security.service.impl;

import com.application.security.dto.EmployeeDTO;
import com.application.security.dto.UserDTO;
import com.application.security.entity.Employee;
import com.application.security.entity.User;
import com.application.security.repository.EmployeeRepository;
import com.application.security.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public boolean isAdminRequest(UserDetails userDetails){
        String roleName = userDetails.getAuthorities().toString();
        roleName=roleName.substring(1, roleName.length()-1);
        return roleName.equalsIgnoreCase("ADMIN");
    }

    @Override
    public ResponseEntity<?> addEmployee(EmployeeDTO employeeData) {
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            Employee employee=modelMapper.map(employeeData, Employee.class);
            Employee savedEmployee=employeeRepository.save(employee);
            if(savedEmployee != null){
                EmployeeDTO convertedEmployee = modelMapper.map(savedEmployee, new TypeToken<EmployeeDTO>(){}.getType());
                map.put("status", 1);
                map.put("data", convertedEmployee);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            else{
                map.put("status", 0);
                map.put("message", "Employee not saved");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){

            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getAllEmployees() {
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            List<Employee> list=employeeRepository.findAll();
            if(!list.isEmpty()){
                List<EmployeeDTO> convertedEmployee = modelMapper.map(list, new TypeToken<List<EmployeeDTO>>(){}.getType());
                map.put("status", 1);
                map.put("data", convertedEmployee);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            else{
                map.put("status", 0);
                map.put("message", "Employee list is empty");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getEmployeeByEmployeeId(int employeeId) {
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            Employee employee=employeeRepository.getReferenceById(employeeId);
            if(employee != null){
                EmployeeDTO convertedEmployee = modelMapper.map(employee, new TypeToken<EmployeeDTO>(){}.getType());
                map.put("status", 1);
                map.put("data", convertedEmployee);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            else{
                map.put("status", 0);
                map.put("message", "Employee not found");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateEmployeeById(EmployeeDTO employeeData) {
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            Employee employee=employeeRepository.getReferenceById(employeeData.getEmployeeId());
            if(employee != null){
                int updateCount=employeeRepository.updateEmployee(employeeData.getJobTitle(),employeeData.getSalary(),employeeData.getDob(),employeeData.getEmployeeId());
                if(updateCount>0){
                    Employee savedEmployee=employeeRepository.getReferenceById(employeeData.getEmployeeId());
                    EmployeeDTO convertedEmployee = modelMapper.map(savedEmployee, new TypeToken<EmployeeDTO>(){}.getType());
                    map.put("status", 1);
                    map.put("data", convertedEmployee);
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
                else{
                    map.put("status", 0);
                    map.put("message", "Employee not updated");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                map.put("status", 0);
                map.put("message", "Employee not found");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteEmployee(int employeeId, UserDetails userDetails) {
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            if(isAdminRequest(userDetails)){
                Employee employee = employeeRepository.getReferenceById(employeeId);
                if(employee != null){
                    employeeRepository.deleteById(employeeId);
                    if(!employeeRepository.existsById(employeeId)){
                        map.put("status", 1);
                        map.put("message", "Employee deleted");
                        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                    }
                    else{
                        map.put("status", 0);
                        map.put("message", "Employee delete failed");
                        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                    }
                }
                else {
                    map.put("status", 0);
                    map.put("message", "Employee not found");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                map.put("status", 0);
                map.put("message", "Unauthorized");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}
