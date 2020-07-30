package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
public class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    private Integer companyId;
    private Integer employeeId;
    @BeforeEach
    public void before() {
        Company company = new Company(1, "ali");
        Company saveCompany = companyRepository.save(company);
        Employee firstEmployee = new Employee(1, 18, "alex", "male", 1000.0,saveCompany.getId());
        Employee secondEmployee = new Employee(2, 18, "alex2", "female", 1000.0,saveCompany.getId());
        Employee thirdEmployee = new Employee(3, 18, "alex3", "male", 1000.0,saveCompany.getId());
        companyId = saveCompany.getId();
        employeeId = employeeRepository.save(firstEmployee).getId();
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
    }

    @AfterEach
    public void after() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
        assert companyRepository.findAll().isEmpty();
        assert employeeRepository.findAll().isEmpty();
    }

    @Test
    public void should_return_employees_when_get_employees_given_none() throws Exception {
        //when then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data[0].name").value("alex"))
                .andExpect(jsonPath("$.data[0].gender").value("male"));
    }

    @Test
    public void should_return_employee_when_post_employee_given_employee() throws Exception {
        //when then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"age\":18,\n" +
                        "    \"name\":\"spy\",\n" +
                        "    \"gender\":\"female\",\n" +
                        "    \"companyId\":" + this.companyId + "\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.name").value("spy"))
                .andExpect(jsonPath("$.data.gender").value("female"));
    }

    @Test
    void should_return_employee_when_update_employee_given_employee_and_id() throws Exception {
        //when then
        mockMvc.perform(put("/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"age\":18,\n" +
                        "    \"name\":\"spy\",\n" +
                        "    \"gender\":\"female\",\n" +
                        "    \"companyId\":" + this.companyId + "\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.name").value("spy"))
                .andExpect(jsonPath("$.data.gender").value("female"));
    }

    @Test
    void should_return_true_when_delete_employee_given_id() throws Exception {
        //when then
        mockMvc.perform(delete("/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    void should_return_employees_when_get_employees_given_page() throws Exception {
        //when then
        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page", String.valueOf(2))
                .param("pageSize", String.valueOf(1)))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("alex2"))
                .andExpect(jsonPath("$.data[0].gender").value("female"));
    }

    @Test
    void should_return_employees_when_get_employees_given_gender() throws Exception {
        //when then
        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("gender", "female"))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("alex2"))
                .andExpect(jsonPath("$.data[0].gender").value("female"));
    }

    @Test
    void should_return_employee_when_get_employee_given_id() throws Exception {
        //when then
        mockMvc.perform(get("/employees/"+employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.name").value("alex"))
                .andExpect(jsonPath("$.data.gender").value("male"));
    }
}
