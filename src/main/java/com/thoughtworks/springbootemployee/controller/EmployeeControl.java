package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

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
    public List<Employee> getEmployees(@RequestParam(defaultValue="null") String page, @RequestParam(defaultValue="null") String pageSize,String gender){
        if(gender!=null){
            return getAllData().stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
        if(!page.equals("null")&&!pageSize.equals("null")){
            return getAllData().stream().skip((Integer.parseInt(page)-1)*Integer.parseInt(pageSize)).limit(Integer.parseInt(pageSize)).collect(Collectors.toList());
        }else{
            return getAllData();
        }

    }
    @GetMapping("/{name}")
    public Employee getSpecifiedNameEmployee(@PathVariable String name){
        return getAllData().stream().filter(employee ->employee.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    @PostMapping("")
    public List<Employee> addEmployee(){
        Employee baiduEmployee = new Employee(5, "baidu1", 20, "male", 10000);
        employees.add(baiduEmployee);
        return employees;
    }

    @PutMapping("/{companyName}")
    public void updateCompany(@PathVariable String employeeName,String newEmployeeName,Integer newEmployeesId,
                              String newEmployeeGender,Integer newEmployeeSalary,Integer newEmployeeAge){
        Employee specifiedEmployee= getSpecifiedNameEmployee(employeeName);
        if(newEmployeeName!=null){
            specifiedEmployee.setName(newEmployeeName);
        }
        if(newEmployeesId!=null){
            specifiedEmployee.setId(newEmployeesId);
        }
        if(newEmployeeGender!=null){
            specifiedEmployee.setGender(newEmployeeGender);
        }
        if(newEmployeeSalary!=null){
            specifiedEmployee.setSalary(newEmployeeSalary);
        }
        if(newEmployeeAge!=null){
            specifiedEmployee.setAge(newEmployeeAge);
        }
    }

    @DeleteMapping("/{employeeName}")
    public List<Employee> deleteEmployee(@PathVariable String employeeName){
        getAllData().remove(getAllData().stream().filter(employee -> employee.getName().equals(employeeName)).collect(Collectors.toList()).get(0));
        return getAllData();
    }

}
