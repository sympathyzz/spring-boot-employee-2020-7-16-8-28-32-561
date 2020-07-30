package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {

    private List<Company> generateCompanyList() {
        List<Company> companies = new ArrayList<>();
        Employee employee1 = new Employee(1, "alibaba1", 17, "male", 6000);
        Employee employee2 = new Employee(2, "alibaba2", 18, "female", 7000);
        Employee employee3 = new Employee(3, "oocl1", 19, "male", 8000);
        Employee employee4 = new Employee(4, "oocl2", 20, "female", 9000);
        List<Employee> alibabaEmployees = new ArrayList<>();
        alibabaEmployees.add(employee1);
        alibabaEmployees.add(employee2);
        List<Employee> OOCLEmployees = new ArrayList<>();
        OOCLEmployees.add(employee3);
        OOCLEmployees.add(employee4);
        Company company1 = new Company(1, "alibaba", 2, alibabaEmployees);
        Company company2 = new Company(2, "oocl", 2, OOCLEmployees);
        companies.add(company1);
        companies.add(company2);
        return companies;
    }

    @Test
    void should_return_company_when_update_given_company_id() {
        //given
        List<Employee> baiduEmployees = new ArrayList<>();
        Employee baiduEmployee1 = new Employee(5, "baidu1", 20, "male", 10000);
        Employee baiduEmployee2 = new Employee(6, "baidu2", 20, "male", 11000);
        baiduEmployees.add(baiduEmployee1);
        baiduEmployees.add(baiduEmployee2);
        Company newCompany = new Company(1, "baidu", 2, baiduEmployees);

        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.findById(1)).thenReturn(
                Optional.of(generateCompanyList().get(0))
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        Integer companyId = 1;
        //when
        Company returnedCompany = companyService.update(companyId, newCompany);
        //then
        assertEquals(newCompany.getId(), returnedCompany.getId());
        assertEquals(newCompany.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(newCompany.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(newCompany.getEmployees(), returnedCompany.getEmployees());
    }

    @Test
    void should_throw_NoSuchDataException_when_update_given_wrong_company_id() {
        //given
        Integer wrong_id = 999;

        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService((mockedCompanyRepository));
        when(mockedCompanyRepository.findById(wrong_id)).thenReturn(
                Optional.ofNullable(null)
        );

        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> companyService.update(wrong_id, new Company()));

        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_return_specifiedCompany_when_find_given_company_id() {
        //given
        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.findById(1)).thenReturn(
                Optional.of(generateCompanyList().get(0))
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        Integer companyId = 1;
        //when
        Company returnCompany = companyService.findById(companyId);

        //then
        assertEquals(companyId, returnCompany.getId());
    }

    @Test
    void should_throw_NoSuchDataException_when_find_given_wrong_company_id() {
        //given
        Integer wrong_id = 999;

        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService((mockedCompanyRepository));
        when(mockedCompanyRepository.findById(wrong_id)).thenReturn(
                Optional.ofNullable(null)
        );

        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> companyService.findById(wrong_id));

        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_return_all_companies_when_get_all_given_no_parameter() {
        //given
        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.findAll()).thenReturn(
                generateCompanyList()
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        List<Company> companies = companyService.getAll();
        //then
        assertEquals(2, companies.size());

    }

    @Test
    void should_return_specified_page_companies_when_get_companies_by_page_given_page_and_page_size() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        when(companyRepository.findAll(PageRequest.of(1, 2))).thenReturn(new PageImpl<>(generateCompanyList()));
        CompanyService companyService = new CompanyService(companyRepository);
        int page = 1;
        int pageSize = 2;

        //when
        List<Company> companies = companyService.getCompanyByPage(page, pageSize).getContent();
        //then
        assertEquals("alibaba", companies.get(page - 1).getCompanyName());
        assertEquals(2, companies.size());
    }

    @Test
    void should_return_success_message_when_delete_company_given_company_name() {
        //given
        Integer wrong_id = 999;
        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.findById(wrong_id)).thenReturn(
                Optional.ofNullable(null)
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        Throwable throwable = assertThrows(NoSuchDataException.class, () -> companyService.deleteCompanyById(wrong_id));
        //then
        assertEquals(NoSuchDataException.class, throwable.getClass());
    }

    @Test
    void should_return_new_company_when_add_given_new_company() {
        //given
        List<Employee> baiduEmployees = new ArrayList<>();
        Employee baiduEmployee1 = new Employee(5, "baidu1", 20, "male", 10000);
        Employee baiduEmployee2 = new Employee(6, "baidu2", 20, "male", 11000);
        baiduEmployees.add(baiduEmployee1);
        baiduEmployees.add(baiduEmployee2);
        Company newCompany = new Company(3, "baidu", 2, baiduEmployees);

        CompanyRepository mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        when(mockedCompanyRepository.save(newCompany)).thenReturn(
                newCompany
        );
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        //when
        Company returnedCompany = companyService.addCompany(newCompany);
        //then
        assertNotNull(returnedCompany);
        assertEquals(returnedCompany, newCompany);
    }

}
