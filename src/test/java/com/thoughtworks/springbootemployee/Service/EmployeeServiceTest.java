package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class EmployeeServiceTest {
    @Test
    void should_return_employee_when_update_given_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findById(1)).thenReturn(
                new Employee(1, "Gavin", 17, "male", 6000)
        );

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Employee newEmployee = new Employee(1, "zach", 18, "male", 1000);

        //when
        Employee returnEmployee = employeeService.update(newEmployee);

        //then
        assertEquals(returnEmployee.getId(), newEmployee.getId());
        assertEquals(returnEmployee.getName(), newEmployee.getName());
        assertEquals(returnEmployee.getAge(), newEmployee.getAge());
        assertEquals(returnEmployee.getGender(), newEmployee.getGender());
        assertEquals(returnEmployee.getSalary(), newEmployee.getSalary());
    }

    @Test
    void should_return_specifiedEmployee_when_find_employee_given_name() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000),
                        new Employee(2, "Zach", 18, "female", 7000)
                )
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        String employeeName1 = "Zach";
        String employeeName2 = "aaa";
        //when
        Employee returnEmployee1 = employeeService.findByName(employeeName1);
        Employee returnEmployee2 = employeeService.findByName(employeeName2);

        //then
        assertEquals(employeeName1, returnEmployee1.getName());
        assertEquals(null, returnEmployee2);
    }

    @Test
    void should_return_all_employee_when_get_all_given_no_parameter() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000),
                        new Employee(2, "Zach", 18, "female", 7000)
                )
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        //when
        List<Employee> employees = employeeService.getAll();
        //then
        assertEquals(2, employees.size());

    }

    @Test
    void should_return_specified_page_employees_when_get_employees_by_page_given_page_and_page_size() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000),
                        new Employee(2, "Zach", 18, "female", 7000)
                )
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        int page = 1;
        int pageSize = 2;

        //when
        List<Employee> employees = employeeService.getEmployeeByPage(page, pageSize);
        //then
        assertEquals("Gavin", employees.get(page - 1).getName());
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_success_message_when_delete_employee_given_employee_name(){
        //given
        Employee employee1=new Employee(1, "Gavin", 17, "male", 6000);
        Employee employee2=new Employee(2, "Zach", 18, "female", 7000);
        List<Employee> employees=new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(employees);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        String employeeName1="Gavin";
        String employeeName2="AAA";
        //when
        String message1 = employeeService.deleteEmployeeByName(employeeName1);
        String message2 = employeeService.deleteEmployeeByName(employeeName2);
        //then
        assertEquals("delete success!",message1);
        assertEquals("delete fail!",message2);
    }

    @Test
    void should_return_new_employee_when_add_given_new_employee(){
        //given
        Employee employee1=new Employee(1, "Gavin", 17, "male", 6000);
        Employee employee2=new Employee(2, "Zach", 18, "female", 7000);
        List<Employee> employees=new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(employees);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Employee newEmployee=new Employee(3, "Spike", 17, "male", 6000);
        //when
        Employee returnedEmployee=employeeService.addEmployee(newEmployee);
        //then
        assertEquals(3,employees.size());
        assertNotNull(returnedEmployee);
    }

}
