package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    private static final String ALIBABA = "alibaba";
    public static final String OOCL = "oocl";

    public List<Company> getAllData(){
        Employee employee1=new Employee(1,"alibaba1",17,"male",6000);
        Employee employee2=new Employee(2,"alibaba2",18,"female",7000);
        Employee employee3=new Employee(3,"oocl1",19,"male",8000);
        Employee employee4=new Employee(4,"oocl2",20,"female",9000);
        List<Employee> ALIBABAEmployees=new ArrayList<>();
        ALIBABAEmployees.add(employee1);
        ALIBABAEmployees.add(employee2);
        List<Employee> OOCLEmployees=new ArrayList<>();
        OOCLEmployees.add(employee3);
        OOCLEmployees.add(employee4);
        Company company1=new Company(ALIBABA,2,ALIBABAEmployees);
        Company company2=new Company(OOCL,2,OOCLEmployees);
        List companies=new ArrayList<Company>();
        companies.add(company1);
        companies.add(company2);
        return companies;
    }

    @GetMapping("")
    public List<Company> getCompanies(){
        return getAllData();
    }

}
