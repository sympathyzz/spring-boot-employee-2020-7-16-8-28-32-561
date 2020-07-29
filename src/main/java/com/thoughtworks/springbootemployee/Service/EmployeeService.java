package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository=employeeRepository;
    }

    public Employee update(Employee newEmployee) {
        EmployeeController employeeController = new EmployeeController();
        List<Employee> employees = employeeController.getAllData();

        for (Employee employee:employees) {
            if(employee.getId() == newEmployee.getId()){
                employee.setAge(newEmployee.getAge());
                employee.setSalary(newEmployee.getSalary());
                employee.setName(newEmployee.getName());
                employee.setGender(newEmployee.getGender());
                return employee;
            }
        }
        return null;
    }

    public Employee findByName(String employeeName) {
        EmployeeController employeeController = new EmployeeController();
        List<Employee> employees = employeeController.getAllData();

        return employees.stream().filter(employee -> {
            return employeeName.equals(employee.getName());
        }).findFirst().orElse(null);
    }
}
