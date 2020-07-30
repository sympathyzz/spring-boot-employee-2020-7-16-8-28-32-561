package com.thoughtworks.springbootemployee.integration;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
