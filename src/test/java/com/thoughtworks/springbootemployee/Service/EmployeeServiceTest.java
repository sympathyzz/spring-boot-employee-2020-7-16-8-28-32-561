package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class EmployeeServiceTest {
    @Test
    void should_return_employee_when_update_given_employee_id(){
        //given
        EmployeeService employeeService = new EmployeeService();
        Employee newEmployee= new Employee(1,"zach",18,"male",1000);

        //when
        Employee returnEmployee = employeeService.update(newEmployee);

        //then
        assertEquals(returnEmployee,newEmployee);
    }

}
