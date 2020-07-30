package com.thoughtworks.springbootemployee.mapper;

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
        assertEquals(company.getCompanyName(), companyRequest.getAge());
        assertEquals(company.getEmployeesNumber(), companyRequest.getCompanyId());
        assertEquals(company.getEmployees(), companyRequest.getName());
    }
}
