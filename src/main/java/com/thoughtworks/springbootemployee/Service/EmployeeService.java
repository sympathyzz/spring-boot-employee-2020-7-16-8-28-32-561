package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;


public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private static final String FAIL_MESSAGE = "fail";
    private static final String SUCCESS_MESSAGE = "success";

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee update(Employee newEmployee) {
        EmployeeController employeeController = new EmployeeController();
        List<Employee> employees = employeeController.getAllData();

        for (Employee employee : employees) {
            if (employee.getId() == newEmployee.getId()) {
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
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().filter(employee -> employee.getName().equals(employeeName)).findFirst().orElse(null);
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public List<Employee> getEmployeeByPage(int page, int pageSize) {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public String deleteEmployeeByName(String employeeName) {
        List<Employee> employees = employeeRepository.findAll();
        Employee specifiedEmployee=findByName(employeeName);
        if (specifiedEmployee!=null) {
            if(employees.remove(specifiedEmployee )){
                return "delete success!";
            }
        }
        return "delete fail!";
    }

    public Employee addEmployee(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        if(employees.add(newEmployee)){
            return newEmployee;
        }
        return null;
    }

    public List<Employee> getEmployeeByGender(String gender) {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }
}
