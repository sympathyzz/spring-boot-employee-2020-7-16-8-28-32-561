package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Service.CompanyService;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public Company getSpecifiedNameCompany(@PathVariable String companyName){
        return companyService.findByName(companyName);
    }
    @GetMapping("/{companyName}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getSpecifiedNameCompanyEmployees(@PathVariable String companyName){
        return companyService.findByName(companyName).getEmployees();
    }


    @GetMapping(params = {"page","pageNum"})
    @ResponseStatus(HttpStatus.OK)
    public Page<Company> getCompanies(@RequestParam Integer page, @RequestParam Integer pageSize){
        return companyService.getCompanyByPage(page,pageSize);
    }

    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Company updateCompany(@PathVariable Integer companyId,@RequestBody Company newCompany){
       return companyService.update(companyId,newCompany);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Company addCompany(Company company){
        return companyService.addCompany(company);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable Integer companyId){
        companyService.deleteCompanyById(companyId);
    }

}
