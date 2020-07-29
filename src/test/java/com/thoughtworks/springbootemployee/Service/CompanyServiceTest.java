package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    private static List<Company> companies=new ArrayList<>();
    private List<Company> generateCompanyList(){
        Employee employee1=new Employee(1,"alibaba1",17,"male",6000);
        Employee employee2=new Employee(2,"alibaba2",18,"female",7000);
        Employee employee3=new Employee(3,"oocl1",19,"male",8000);
        Employee employee4=new Employee(4,"oocl2",20,"female",9000);
        List<Employee> alibabaEmployees=new ArrayList<>();
        alibabaEmployees.add(employee1);
        alibabaEmployees.add(employee2);
        List<Employee> OOCLEmployees=new ArrayList<>();
        OOCLEmployees.add(employee3);
        OOCLEmployees.add(employee4);
        Company company1=new Company(1,"alibaba",2,alibabaEmployees);
        Company company2=new Company(2,"oocl",2,OOCLEmployees);
        if(companies.size()==0){
            companies.add(company1);
            companies.add(company2);
        }
        return companies;
    }

    @Test
    void should_return_company_when_update_given_new_company() {
        //given
        List<Employee> baiduEmployees=new ArrayList<>();
        Employee baiduEmployee1 = new Employee(5, "baidu1", 20, "male", 10000);
        Employee baiduEmployee2 = new Employee(6, "baidu2", 20, "male", 11000);
        baiduEmployees.add(baiduEmployee1);
        baiduEmployees.add(baiduEmployee2);
        Company newCompany=new Company(1,"baidu",2,baiduEmployees);

        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.findById(1)).thenReturn(
                generateCompanyList().stream().filter(company -> company.getId()==1).findFirst().orElse(null)
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        Company returnedCompany=companyService.update(newCompany);
        //then
        assertEquals(newCompany.getId(), returnedCompany.getId());
        assertEquals(newCompany.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(newCompany.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(newCompany.getEmployees(), returnedCompany.getEmployees());
    }
}
