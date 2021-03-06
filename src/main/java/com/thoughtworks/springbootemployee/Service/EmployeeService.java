package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee update(Integer employeeId, Employee newEmployee) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()) {
            throw new NoSuchDataException();
        }
        if(newEmployee.getId()!=employeeId){
            throw new IllegalOperationException();
        }
        if (newEmployee.getName() != null) {
            employee.get().setName(newEmployee.getName());
        }
        if (newEmployee.getAge() != null) {
            employee.get().setAge(newEmployee.getAge());
        }
        if (newEmployee.getGender() != null) {
            employee.get().setGender(newEmployee.getGender());
        }
        if (newEmployee.getSalary() != null) {
            employee.get().setSalary(newEmployee.getSalary());
        }
        employeeRepository.save(employee.get());
        return employee.get();
    }


    public Employee findById(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent())
            throw new NoSuchDataException();
        return employee.get();
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Page<Employee> getEmployeeByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }


    public Boolean deleteEmployeeById(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::delete);
        if (!employee.isPresent()) {
            throw new NoSuchDataException();
        }
        return true;
    }

    public Employee addEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }
}
