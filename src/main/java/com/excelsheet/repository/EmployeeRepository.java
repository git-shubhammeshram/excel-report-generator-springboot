package com.excelsheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excelsheet.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
