package com.thoughtworks.springbootemployee.integration;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
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

    @Autowired
    private EmployeeRepository employeeRepository;


    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @BeforeEach
    void init(){
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        companyRepository.save(new Company(null, "oocl", 2, null));
    }

    @Test
    void should_return_company_when_add_given_company() throws Exception {
        //given
        Company newCompany =new Company(null,"alibaba1",3,null);
        String companyJson = JSONObject.toJSONString(newCompany);

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(newCompany.getEmployeesNumber()))
                .andExpect(jsonPath("$.employees").value(newCompany.getEmployees()));
    }

    @Test
    void should_return_202_when_delete_given_company_id() throws Exception {
        //given
        Integer id=companyRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/companies/"+id))
                .andExpect(status().isAccepted());
    }

    @Test
    void should_return_company_when_update_given_company_id_and_company() throws Exception {
        //given
        Integer id=companyRepository.findAll().get(0).getId();
        Company company = new Company(id,"alibaba2",4,null);
        String employeeJson = JSONObject.toJSONString(company);

        mockMvc.perform(put("/companies/"+id).contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));
    }

    @Test
    void should_return_companies_when_find_all_given_no_parameter() throws Exception {
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)));
    }

    @Test
    void should_return_company_when_find_given_company_id() throws Exception {
        //given
        Integer id = companyRepository.findAll().get(0).getId();

        mockMvc.perform(get("/companies/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void should_return_companies_when_find_given_page_and_page_size() throws Exception {
        //given
        Integer page=1;
        Integer pageSize=1;

        mockMvc.perform(get("/companies?page="+page+"&pageSize="+pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[0].companyName").value("oocl"));
    }

    @Test
    void should_return_employees_when_find_given_company_id() throws Exception {
        //given
        Integer id = companyRepository.findAll().get(0).getId();
        Employee employee1 = new Employee(null, "Zach1", 18, "female", 7000,id);
        Employee employee2 = new Employee(null, "Zach2", 18, "female", 7000,id);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        mockMvc.perform(get("/companies/"+id+"/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[1].name").value(employee2.getName()));
    }
}
