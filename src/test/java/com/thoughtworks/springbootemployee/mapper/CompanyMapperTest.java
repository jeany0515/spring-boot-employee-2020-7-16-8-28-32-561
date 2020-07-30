package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyRespond;
import com.thoughtworks.springbootemployee.model.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyMapperTest {
    @Test
    void should_return_employee_when_mapper_given_employee_request() {
        //given
        CompanyRequest companyRequest = new CompanyRequest(1, "ali", 1000, new ArrayList<>());
        //when
        Company company = CompanyMapper.map(companyRequest);
        //then
        assertEquals(company.getId(), companyRequest.getId());
        assertEquals(company.getCompanyName(), companyRequest.getCompanyName());
        assertEquals(company.getEmployeesNumber(), companyRequest.getEmployeesNumber());
        assertEquals(company.getEmployees(), companyRequest.getEmployees());
    }

    @Test
    void should_return_employee_respond_when_mapper_given_employee() {
        //given
        Company company = new Company(1, "ali", 1000, new ArrayList<>());
        //when
        CompanyRespond companyRespond = CompanyMapper.map(company);
        //then
        assertEquals(company.getId(), companyRespond.getId());
        assertEquals(company.getCompanyName(), companyRespond.getCompanyName());
        assertEquals(company.getEmployeesNumber(), companyRespond.getEmployeesNumber());
        assertEquals(company.getEmployees(), companyRespond.getEmployees());
    }
}
