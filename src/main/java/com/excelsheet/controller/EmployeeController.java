package com.excelsheet.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excelsheet.entity.Employee;
import com.excelsheet.service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Display Employee Form and List
    @GetMapping("/employees")
    public String getEmployees(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    // Handle Employee Form Submission
    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("addedSuccessfully", true);
        return "redirect:/employees"; // Redirect to show updated list and success message
    }

    // Download Excel File
    @GetMapping("/employees/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

        List<Employee> employees = employeeService.getAllEmployees();
        ByteArrayInputStream stream = employeeService.generateExcel(employees);

        IOUtils.copy(stream, response.getOutputStream());
        response.flushBuffer();
    }
}
