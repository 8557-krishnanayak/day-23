package com.godigit.SpringEmployee.controller;

import com.godigit.SpringEmployee.model.Employee;
import com.godigit.SpringEmployee.service.EmployeeService;
import com.opencsv.CSVReader;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/upload/csv")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a CSV file to upload.";
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> allData = reader.readAll().stream().skip(1).toList();
            List<Employee> empList = new ArrayList<>();

            // Process each data entry
            for (String[] row : allData) {
                // Process each row
                System.out.println("Processing row: " + Arrays.toString(row));

                Employee employee = Employee.builder().firstName(row[1]).lastName(row[2])
                        .email(row[3]).department(row[4])
                        .salary(Double.parseDouble(row[5])).build();

                empList.add(employee);
            }

            employeeService.addMultipleData(empList);
        } catch (Exception e) {
            return "An error occurred while processing the file.";
        }

        return "File uploaded and processed successfully.";
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id), HttpStatus.OK);
    }


}
