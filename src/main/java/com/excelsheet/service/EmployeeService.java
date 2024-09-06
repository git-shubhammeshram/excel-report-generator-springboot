package com.excelsheet.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelsheet.entity.Employee;
import com.excelsheet.repository.EmployeeRepository;

@Service
public class EmployeeService {


	    @Autowired
	    private EmployeeRepository employeeRepository;

	    public List<Employee> getAllEmployees() {
	        return employeeRepository.findAll();
	    }

	    public void saveEmployee(Employee employee) {
	        employeeRepository.save(employee);
	    }

	    public ByteArrayInputStream generateExcel(List<Employee> employees) throws IOException {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Employees");

	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("ID");
	        header.createCell(1).setCellValue("Name");
	        header.createCell(2).setCellValue("Department");
	        header.createCell(3).setCellValue("Salary");

	        int rowIdx = 1;
	        for (Employee employee : employees) {
	            Row row = sheet.createRow(rowIdx++);
	            row.createCell(0).setCellValue(employee.getId());
	            row.createCell(1).setCellValue(employee.getName());
	            row.createCell(2).setCellValue(employee.getDepartment());
	            row.createCell(3).setCellValue(employee.getSalary());
	        }

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        workbook.write(out);
	        workbook.close();
	        return new ByteArrayInputStream(out.toByteArray());
	    }
	}

