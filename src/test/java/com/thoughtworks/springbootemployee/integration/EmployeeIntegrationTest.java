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
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @BeforeEach
    void init() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        companyRepository.save(new Company(null, "oocl", 2, null));
        Integer id = companyRepository.findAll().get(0).getId();
        employeeRepository.save(new Employee(null, "Gavin", 18, "female", 7000, id));
        employeeRepository.save(new Employee(null, "Zach", 18, "male", 7000, id));
    }

    @Test
    void should_return_employee_when_add_given_employee() throws Exception {
        //given
        Integer id = companyRepository.findAll().get(0).getId();
        Employee employee = new Employee(null, "Zach", 18, "female", 7000, id);
        String employeeJson = JSONObject.toJSONString(employee);

        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.age").value(employee.getAge()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));

    }

    @Test
    void should_return_202_when_delete_given_employee_id() throws Exception {
        Integer id = employeeRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/employees/" + id))
                .andExpect(status().isAccepted());
    }

    @Test
    void should_return_employee_when_update_given_employee_id_and_employee() throws Exception {
        //given
        Integer id = employeeRepository.findAll().get(0).getId();
        Employee employee = new Employee(id, "Zach2", 19, "female", 8000, 1);
        String employeeJson = JSONObject.toJSONString(employee);

        mockMvc.perform(put("/employees/" + id).contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.age").value(employee.getAge()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    void should_return_employees_when_find_all_given_no_parameter() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)));
    }

    @Test
    void should_return_employee_when_find_given_employee_id() throws Exception {
        //given
        Integer id = employeeRepository.findAll().get(0).getId();

        mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void should_return_employees_when_find_given_page_and_page_size() throws Exception {
        //given
        Integer page = 1;
        Integer pageSize = 2;
        Integer id1 = employeeRepository.findAll().get(0).getId();
        Integer id2 = employeeRepository.findAll().get(1).getId();


        mockMvc.perform(get("/employees?page=" + page + "&pageSize=" + pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[1].id").value(id2));
    }

    @Test
    void should_return_male_employees_when_find_given_male_gender() throws Exception {
        //given
        String gender = "male";
        mockMvc.perform(get("/employees?gender=" + gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gender").value(gender));
    }

}
