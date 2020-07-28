package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeControl {
    public static List<Employee> employees=new ArrayList<>();
    public List<Employee> getAllData(){
        Employee employee1=new Employee(1,"alibaba1",17,"male",6000);
        Employee employee2=new Employee(2,"alibaba2",18,"female",7000);
        Employee employee3=new Employee(3,"oocl1",19,"male",8000);
        Employee employee4=new Employee(4,"oocl2",20,"female",9000);
        if(employees.size()==0){
            employees.add(employee1);
            employees.add(employee2);
            employees.add(employee3);
            employees.add(employee4);
        }
        return employees;
    }

    @GetMapping("")
    public List<Employee> getEmployees(){
        return getAllData();
    }
    @GetMapping("/{name}")
    public Employee getSpecifiedNameEmployee(@PathVariable String name){
        return getAllData().stream().filter(employee ->employee.getName().equals(name)).collect(Collectors.toList()).get(0);
    }
}
