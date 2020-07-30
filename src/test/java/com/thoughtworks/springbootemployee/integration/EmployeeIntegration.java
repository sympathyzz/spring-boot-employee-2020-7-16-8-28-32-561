package com.thoughtworks.springbootemployee.integration;


import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_employee_when_add_given_employee() throws Exception {
        //given
        Employee employee = new Employee(null, "Zach", 18, "female", 7000);
        String employeeJson = JSONObject.toJSONString(employee);

        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.age").value(employee.getAge()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));

    }

    @Test
    void should_return_202_when_delete_given_employee_id() throws Exception {
        //given
        employeeRepository.save(new Employee(1, "Zach", 18, "female", 7000));

        mockMvc.perform(delete("/employees/"+1))
                .andExpect(status().isAccepted());

    }

    @Test
    void should_return_employee_when_update_given_employee_id_and_employee() throws Exception {
        //given
        employeeRepository.save(new Employee(1, "Zach", 18, "female", 7000));
        Employee employee = new Employee(1, "Zach2", 19, "female", 8000);
        String employeeJson = JSONObject.toJSONString(employee);

        mockMvc.perform(put("/employees/"+1).contentType(MediaType.APPLICATION_JSON).content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.age").value(employee.getAge()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    void should_return_employees_when_find_all_given_no_parameter() throws Exception {
        //given
        employeeRepository.save(new Employee(1, "Zach", 18, "female", 7000));
        employeeRepository.save(new Employee(2, "Zach2", 18, "female", 7000));
        employeeRepository.save(new Employee(3, "Zach3", 18, "female", 7000));

        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_return_employee_when_find_given_employee_id() throws Exception {
        //given
        employeeRepository.save(new Employee(1, "Zach", 18, "female", 7000));
        Integer id=1;

        mockMvc.perform(get("/employees/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

}
