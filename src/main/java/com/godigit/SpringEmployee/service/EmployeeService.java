package com.godigit.SpringEmployee.service;

import com.godigit.SpringEmployee.model.Employee;
import com.godigit.SpringEmployee.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    //    createEmployee,
    public String createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Insertion done";
    }

    // getEmployeeById
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such user exist with this id"));
    }

    // getAllEmployees
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    // updateEmployee
    public String updateEmployee(Long id,Employee e) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(e.getFirstName());
        employee.setLastName(e.getLastName());
        employee.setEmail(e.getEmail());
        employee.setSalary(e.getSalary());
        employeeRepository.save(employee);
        return "changes done";
    }

    //deleteEmployee.
    public String deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
        return "Employee id:\t" + id + " [DELETE]";
    }
}
