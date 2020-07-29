package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;


public class EmployeeServiceTest {
    @Test
    void should_return_employee_when_update_given_employee_id(){
        //given
        EmployeeRepository mockedEmployeeRepository= Mockito.mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findById(1)).willReturn(
                new Employee(1,"Gavin",17,"male",6000)
        );

        EmployeeService employeeService = new EmployeeService();
        Employee newEmployee= new Employee(1,"zach",18,"male",1000);

        //when
        Employee returnEmployee = employeeService.update(newEmployee);

        //then
        assertEquals(returnEmployee.getId(),newEmployee.getId());
        assertEquals(returnEmployee.getName(),newEmployee.getName());
        assertEquals(returnEmployee.getAge(),newEmployee.getAge());
        assertEquals(returnEmployee.getGender(),newEmployee.getGender());
        assertEquals(returnEmployee.getSalary(),newEmployee.getSalary());
    }

    @Test
    void should_return_specifiedEmployee_when_find_employee_given_name(){
        //given
        EmployeeService employeeService = new EmployeeService();
        String employeeName1="Zach";
        String employeeName2="aaa";
        //when
        Employee returnEmployee1 = employeeService.findByName(employeeName1);
        Employee returnEmployee2 = employeeService.findByName(employeeName2);

        //then
        assertEquals(employeeName1,returnEmployee1.getName());
        assertEquals(null,returnEmployee2);
    }

}
