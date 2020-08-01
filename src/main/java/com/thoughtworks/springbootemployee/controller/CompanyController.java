package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Service.CompanyService;
import com.thoughtworks.springbootemployee.dto.CompanyDto;
import com.thoughtworks.springbootemployee.dto.EmployeeDto;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyDto> getCompanies(){
        return companyService.getAll().stream().map(this::companyConvertToDto).collect(Collectors.toList());
    }

    private CompanyDto companyConvertToDto(Company company) {
        CompanyDto companyDto=modelMapper.map(company,CompanyDto.class);
        return companyDto;
    }

    private EmployeeDto employeeConvertToDto(Employee employee) {
        EmployeeDto employeeDto=modelMapper.map(employee,EmployeeDto.class);
        return employeeDto;
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto getSpecifiedIdCompany(@PathVariable Integer companyId){
        return companyConvertToDto(companyService.findById(companyId));
    }
    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getSpecifiedNameCompanyEmployees(@PathVariable Integer companyId){
        return companyService.getEmployeesByCompanyId(companyId).stream().map(this::employeeConvertToDto).collect(Collectors.toList());
    }


    @GetMapping(params = {"page","pageNum"})
    @ResponseStatus(HttpStatus.OK)
    public Page<CompanyDto> getCompanies(@RequestParam Integer page, @RequestParam Integer pageSize){
        return new PageImpl<>(companyService.getCompanyByPage(page,pageSize).stream().map(this::companyConvertToDto).collect(Collectors.toList()));
    }

    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto updateCompany(@PathVariable Integer companyId,@RequestBody Company newCompany){
       return companyConvertToDto(companyService.update(companyId,newCompany));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto addCompany(@RequestBody Company company){
        return companyConvertToDto(companyService.addCompany(company));
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCompany(@PathVariable Integer companyId){
        companyService.deleteCompanyById(companyId);
    }

}
