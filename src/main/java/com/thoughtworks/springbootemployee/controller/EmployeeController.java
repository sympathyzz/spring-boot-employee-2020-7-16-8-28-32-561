package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private static List<Employee> employees=new ArrayList<>();
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

    @GetMapping
    public List<Employee> getEmployees(@RequestParam(defaultValue="0") Integer page, @RequestParam(defaultValue="0") Integer pageSize,@RequestParam(required = false)String gender){
        if(gender!=null){
            return getAllData().stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
        if(page!=0&&pageSize!=0){
            return getAllData().stream().skip((page-1)*pageSize).limit(pageSize).collect(Collectors.toList());
        }else{
            return getAllData();
        }

    }
    @GetMapping("/{name}")
    public Employee getSpecifiedNameEmployee(@PathVariable String name){
        return getAllData().stream().filter(employee ->employee.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    @PostMapping
    public Employee addEmployee(Employee employee){
        if(getAllData().add(employee)){
            return employee;
        }
        return null;
    }

    @PutMapping("/{employeeName}")
    public Employee updateEmployee(@PathVariable String employeeName,@RequestBody Employee employee){
        Employee specifiedEmployee= getSpecifiedNameEmployee(employeeName);
            if(employee!=null){
                specifiedEmployee.setName(employee.getName());
                specifiedEmployee.setId(employee.getId());
                specifiedEmployee.setGender(employee.getGender());
                specifiedEmployee.setSalary(employee.getSalary());
                specifiedEmployee.setAge(employee.getAge());
                return specifiedEmployee;
            }
        return null;
    }

    @DeleteMapping("/{employeeName}")
    public List<Employee> deleteEmployee(@PathVariable String employeeName){
        getAllData().remove(getAllData().stream().filter(employee -> employee.getName().equals(employeeName)).collect(Collectors.toList()).get(0));
        return getAllData();
    }

}
