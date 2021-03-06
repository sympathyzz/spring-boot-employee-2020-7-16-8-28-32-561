package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class EmployeeServiceTest {
    @Test
    void should_return_employee_when_update_given_employee_id() throws Exception{
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findById(1)).thenReturn(
                Optional.of(new Employee(1, "Gavin", 17, "male", 6000,1))
        );

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Employee newEmployee = new Employee(1, "zach", 18, "male", 1000,1);

        //when
        Employee returnEmployee = employeeService.update(1, newEmployee);

        //then
        assertEquals(returnEmployee.getId(), newEmployee.getId());
        assertEquals(returnEmployee.getName(), newEmployee.getName());
        assertEquals(returnEmployee.getAge(), newEmployee.getAge());
        assertEquals(returnEmployee.getGender(), newEmployee.getGender());
        assertEquals(returnEmployee.getSalary(), newEmployee.getSalary());
    }

    @Test
    void should_throw_NoSuchDataException_when_update_given_wrong_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        Integer wrong_id = 9999;
        Employee newEmployee = new Employee(1, "zach", 18, "male", 1000,1);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        when(mockedEmployeeRepository.findById(wrong_id)).thenReturn(Optional.ofNullable(null));

        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> employeeService.update(1, newEmployee));

        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_throw_IllegalOperationException_when_update_given_wrong_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        Employee newEmployee = new Employee(2, "zach2", 18, "male", 1000,1);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        when(mockedEmployeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "Gavin", 17, "male", 6000,1)));

        //when
        Throwable throwable = assertThrows(IllegalOperationException.class, () -> employeeService.update(1, newEmployee));

        //then
        assertEquals(IllegalOperationException.class, throwable.getClass());
    }

    @Test
    void should_return_specifiedEmployee_when_find_employee_given_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findById(1)).thenReturn(
                Optional.of(new Employee(1, "Gavin", 17, "male", 6000,1))
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        Integer employeeId = 1;
        //when
        Employee returnEmployee = employeeService.findById(employeeId);

        //then
        assertNotNull(returnEmployee);
        assertEquals("Gavin", returnEmployee.getName());

    }

    @Test
    void should_throw_NoSuchDataException_when_find_employee_given_wrong_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        Integer wrong_id = 9999;
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        when(mockedEmployeeRepository.findById(wrong_id)).thenReturn(Optional.ofNullable(null));

        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> employeeService.findById(wrong_id));

        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_return_all_employee_when_get_all_given_no_parameter() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000,1),
                        new Employee(2, "Zach", 18, "female", 7000,1)
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
        when(mockedEmployeeRepository.findAll(PageRequest.of(1, 2))).thenReturn(
                new PageImpl<>(Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000,1),
                        new Employee(2, "Zach", 18, "female", 7000,1)
                ))
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        int page = 1;
        int pageSize = 2;
        //when
        Page<Employee> employees = employeeService.getEmployeeByPage(page, pageSize);
        //then
        assertEquals("Gavin", employees.getContent().get(0).getName());
        assertEquals(2, employees.getSize());
    }

    @Test
    void should_return_success_message_when_delete_employee_given_employee_id() {
        //given
        Integer employeeId = 1;
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee(1,"gavin",20,"male",1000,1)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        //when
        Boolean result = employeeService.deleteEmployeeById(employeeId);
        //then
        assertEquals(true, result);
    }

    @Test
    void should_throw_NoSuchDataException_when_delete_employee_given_wrong_employee_id() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        Integer wrong_id = 9999;
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);

        when(mockedEmployeeRepository.findById(wrong_id)).thenReturn(Optional.ofNullable(null));

        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> employeeService.deleteEmployeeById(wrong_id));

        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_return_new_employee_when_add_given_new_employee() {
        //given
        Employee employee = new Employee(1, "Gavin", 17, "male", 6000,1);
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.save(employee)).thenReturn(employee);
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        //when
        Employee returnedEmployee = employeeService.addEmployee(employee);
        //then
        assertNotNull(returnedEmployee);
        assertEquals(returnedEmployee, employee);
    }

    @Test
    void should_return_male_employees_when_get_employee_given_male_gender() {
        //given
        EmployeeRepository mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        when(mockedEmployeeRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Employee(1, "Gavin", 17, "male", 6000,1),
                        new Employee(2, "Zach", 18, "female", 7000,1)
                )
        );
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        String gender = "male";
        //when
        List<Employee> returnedEmployees = employeeService.getEmployeeByGender(gender);
        //then
        for (Employee employee : returnedEmployees) {
            assertEquals("male", employee.getGender());
        }
    }

}
