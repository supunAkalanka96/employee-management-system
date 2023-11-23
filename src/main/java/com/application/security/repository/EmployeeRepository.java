package com.application.security.repository;

import com.application.security.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Transactional
    @Modifying
    @Query("update Employee e set e.jobTitle = ?1, e.salary=?2, e.dob=?3 where e.employeeId = ?4")
    int updateEmployee(@NonNull String jobTitle, double salary, Date dob, int employeeId);
}
