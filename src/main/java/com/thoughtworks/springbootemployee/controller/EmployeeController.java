package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Service.EmployeeService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployees(){
        return employeeService.getAll().stream().map(this::employeeConvertToDto).collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageNum"})
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeDto> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer pageSize){
        return new PageImpl<>(employeeService.getEmployeeByPage(page,pageSize).stream().map(this::employeeConvertToDto).collect(Collectors.toList()));

    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployeesByGender(@RequestParam String gender){
        return employeeService.getEmployeeByGender(gender).stream().map(this::employeeConvertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getSpecifiedIdEmployee(@PathVariable Integer employeeId){
        return employeeConvertToDto(employeeService.findById(employeeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto addEmployee(@RequestBody Employee employee){
        return  employeeConvertToDto(employeeService.addEmployee(employee));
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto updateEmployee(@PathVariable Integer employeeId,@RequestBody Employee employee) throws Exception {
        return employeeConvertToDto(employeeService.update(employeeId,employee));
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmployee(@PathVariable Integer employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }

    private EmployeeDto employeeConvertToDto(Employee employee) {
        EmployeeDto employeeDto=modelMapper.map(employee,EmployeeDto.class);
        return employeeDto;
    }

}
