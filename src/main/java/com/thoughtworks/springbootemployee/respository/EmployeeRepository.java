package com.thoughtworks.springbootemployee.respository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByName(String employeeName);

    Employee add(Employee newEmployee);

    List<Employee> findByGender(String gender);
}
