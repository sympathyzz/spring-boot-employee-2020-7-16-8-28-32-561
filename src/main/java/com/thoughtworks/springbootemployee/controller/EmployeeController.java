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

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getSpecifiedIdEmployee(@PathVariable Integer employeeId){
        return employeeService.findById(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(Employee employee){
        return  employeeService.addEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable Integer employeeId,@RequestBody Employee employee){
        return employeeService.update(employeeId,employee);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmployee(@PathVariable Integer employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }

}
