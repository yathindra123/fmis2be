package com.ugnip.repositories;

import com.ugnip.models.employee.Employee;
import com.ugnip.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Employee, String> {
    public Employee findByEmailAndPassword(String email, String password);
}
