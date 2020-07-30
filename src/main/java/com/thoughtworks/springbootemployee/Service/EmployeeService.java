package com.thoughtworks.springbootemployee.Service;

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

    public Employee update(Integer employeeId,Employee newEmployee) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee!=null){
            if (newEmployee.getName()!=null){
                employee.get().setName(newEmployee.getName());
            }
            if (newEmployee.getAge()!=null){
                employee.get().setAge(newEmployee.getAge());
            }
            if (newEmployee.getGender()!=null){
                employee.get().setGender(newEmployee.getGender());
            }
            if (newEmployee.getSalary()!=null){
                employee.get().setSalary(newEmployee.getSalary());
            }
            employeeRepository.save(employee.get());
        }
        return employee.get();
    }

    public Employee findByName(String employeeName) {
         return employeeRepository.findByName(employeeName).get();
    }

    public Employee findById(Integer employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Page<Employee> getEmployeeByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page,pageSize));
    }

    public void deleteEmployeeByName(String employeeName) {
       Optional<Employee> employee=employeeRepository.findByName(employeeName);
       employee.ifPresent(employeeRepository::delete);
    }

    public Boolean deleteEmployeeById(Integer employeeId) {
        Optional<Employee> employee=employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::delete);
        if(employeeRepository.findById(employeeId)==null){
            return false;
        }
        return true;
    }

    public Employee addEmployee(Employee newEmployee) {
        return employeeRepository.add(newEmployee);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }
}
