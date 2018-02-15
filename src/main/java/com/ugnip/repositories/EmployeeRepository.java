package com.ugnip.repositories;

import com.ugnip.models.employee.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    public Employee findById(String id);
    public Employee findByEmpId(String id);
    public List<Employee> findAll();
    Employee findOne(String id);
    public void deleteByEmpId(String id);
    public Employee findByEmailAndPassword(String email, String password);
    public Employee findByName(String name);

    public Employee findByEmail(String email);
}
