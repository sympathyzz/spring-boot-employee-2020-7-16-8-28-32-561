package com.thoughtworks.springbootemployee.integration;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;


    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_company_when_add_given_company() throws Exception {
        //given
        Company newCompany =new Company(1,"alibaba1",3,null);
        String companyJson = JSONObject.toJSONString(newCompany);

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newCompany.getId()))
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(newCompany.getEmployeesNumber()))
                .andExpect(jsonPath("$.employees").value(newCompany.getEmployees()));
    }

    @Test
    void should_return_202_when_delete_given_company_id() throws Exception {
        //given
        companyRepository.save(new Company(1,"alibaba1",3,null));

        mockMvc.perform(delete("/companies/"+1))
                .andExpect(status().isAccepted());

    }

    @Test
    void should_return_company_when_update_given_company_id_and_company() throws Exception {
        //given
        companyRepository.save(new Company(1,"alibaba1",3,null));
        Company company = new Company(1,"alibaba2",4,null);
        String employeeJson = JSONObject.toJSONString(company);

        mockMvc.perform(put("/companies/"+1).contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));
    }

    @Test
    void should_return_companies_when_find_all_given_no_parameter() throws Exception {
        //given
        companyRepository.save(new Company(1,"alibaba1",3,null));
        companyRepository.save(new Company(2,"alibaba2",3,null));
        companyRepository.save(new Company(3,"alibaba3",3,null));

        mockMvc.perform(get("/companies/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

}
