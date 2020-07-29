package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Service.EmployeeService;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private static List<Employee> employees=new ArrayList<>();

    @Autowired
    EmployeeService employeeService;

    public List<Employee> getAllData(){
        Employee employee1=new Employee(1,"Gavin",17,"male",6000);
        Employee employee2=new Employee(2,"Zach",18,"female",7000);
        Employee employee3=new Employee(3,"Albert",19,"male",8000);
        Employee employee4=new Employee(4,"Penny",20,"female",9000);
        if(employees.size()==0){
            employees.add(employee1);
            employees.add(employee2);
            employees.add(employee3);
            employees.add(employee4);
        }
        return employees;
    }

    @GetMapping(params = {"page","pageNum"})
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> getEmployees(@RequestParam Integer page, @RequestParam Integer pageSize){
            return employeeService.getEmployeeByPage(page,pageSize);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees(@RequestParam String gender){
        return employeeService.getEmployeeByGender(gender);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getSpecifiedNameEmployee(@PathVariable String name){
        return employeeService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(Employee employee){
        return  employeeService.addEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@PathVariable Integer employeeId,@RequestBody Employee employee){
        return employeeService.update(employeeId,employee);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Integer employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }

}
