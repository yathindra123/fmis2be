package com.ugnip.controllers;

import com.ugnip.repositories.EmployeeRepository;
import com.ugnip.models.employee.Employee;
//import com.ugnip.services.EmployeeService;
//import com.ugnip.utils.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //View all Employees
    @GetMapping
    public List<Employee> viewAllEmployees(){
        Sort sortEmployeesByDescOrder = new Sort(Sort.Direction.DESC, "createdAt");
        return employeeRepository.findAll(sortEmployeesByDescOrder);
    }

    //Create a new employee
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee){

        //Save the new employee
        return employeeRepository.save(employee);
    }

    //Find an employee by name
    @GetMapping(value="/{name}")
    public Employee findEmployeeByName(@PathVariable("name") String name) {
        Employee employee = employeeRepository.findByName(name);

        if(employee != null) {
            return employee;
        } else {
            return null;
        }
    }

    // Change employee details
    @PutMapping(value="/{empId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("empId") String empId,
                                                   @Valid @RequestBody Employee employee) {
        Employee employeeData = employeeRepository.findByEmpId(empId);

        //If the employee not found return not found
        if(employeeData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //If item found update it
        employeeData.setEmpId(employee.getEmpId());
        employeeData.setName(employee.getName());
        employeeData.setPassword(employee.getPassword());
        employeeData.setEmail(employee.getEmail());
        employeeData.setEmpType(employee.getEmpType());
        employeeData.setBirthday(employee.getBirthday());
        Employee updatedEmployee = employeeRepository.save(employeeData);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete employee buy employee id
    @DeleteMapping(value="/{id}")
    public void deleteTodo(@PathVariable("id") String id) {
        employeeRepository.deleteByEmpId(id);
    }

}
