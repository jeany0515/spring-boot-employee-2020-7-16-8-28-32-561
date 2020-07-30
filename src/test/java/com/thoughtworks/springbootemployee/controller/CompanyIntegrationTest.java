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
public class CompanyIntegrationTest {
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

        Company companyOfByte = new Company(1, "byte");
        Company saveCompanyOfByte = companyRepository.save(companyOfByte);
        Employee firstEmployeeOfByte = new Employee(1, 18, "alex4", "male", 1000.0,saveCompanyOfByte.getId());
        Employee secondEmployeeOfByte = new Employee(2, 18, "alex5", "female", 1000.0,saveCompanyOfByte.getId());
        Employee thirdEmployeeOfByte = new Employee(3, 18, "alex6", "male", 1000.0,saveCompanyOfByte.getId());
        companyId = saveCompanyOfByte.getId();
        employeeId = employeeRepository.save(firstEmployeeOfByte).getId();
        employeeRepository.save(secondEmployeeOfByte);
        employeeRepository.save(thirdEmployeeOfByte);
    }

    @AfterEach
    public void after() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
        assert companyRepository.findAll().isEmpty();
        assert employeeRepository.findAll().isEmpty();
    }

    @Test
    void should_return_companies_when_get_companies_given_none() throws Exception {
        //when then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].companyName").value("ali"))
                .andExpect(jsonPath("$.data[0].employees", hasSize(3)));
    }

    @Test
    void should_return_companies_when_get_companies_given_page() throws Exception {
        //when then
        mockMvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page", String.valueOf(2))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].companyName").value("byte"))
                .andExpect(jsonPath("$.data[0].employees", hasSize(3)));
    }

    @Test
    void should_return_company_when_get_company_given_id() throws Exception {
        //when then
        mockMvc.perform(get("/companies/" + companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.companyName").value("byte"))
                .andExpect(jsonPath("$.data.employees", hasSize(3)));
    }

    @Test
    void should_return_employees_when_get_company_employees_given_id() throws Exception {
        //when then
        mockMvc.perform(get("/companies/" + companyId + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].name").value("alex4"))
                .andExpect(jsonPath("$.data[0].gender").value("male"));
    }

    @Test
    void should_return_company_when_add_company_given_company() throws Exception {
        //when then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"companyName\":\"alibaba\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.companyName").value("alibaba"));
    }

    @Test
    void should_return_company_when_update_company_given_company_and_id() throws Exception {
        //when then
        mockMvc.perform(put("/companies/" + companyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"companyName\":\"alibabaaaaaaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.companyName").value("alibabaaaaaaa"));
    }

    @Test
    void should_return_boolean_when_delete_company_given_id() throws Exception {
        //when then
        mockMvc.perform(delete("/companies/" + companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("success"));
    }
}
