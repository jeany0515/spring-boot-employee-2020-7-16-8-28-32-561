package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyRespond;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    @Test
    public void should_return_companies_when_get_companies_given_none() throws NotFoundException {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        List<Company> companies = new ArrayList<>();
        ArrayList<Employee> employeesOfOOCL = new ArrayList<>();
        ArrayList<Employee> employeesOfBlibli = new ArrayList<>();
        companies.add(new Company(1, "oocl"));
        companies.add(new Company(2, "blibli"));
        employeesOfOOCL.add(new Employee(1001, 18, "zach", "male", 1000.0,1));
        employeesOfOOCL.add(new Employee(1002, 17, "alex", "male", 1000.0,1));
        employeesOfBlibli.add(new Employee(1003, 19, "zach1", "male", 1000.0,1));
        employeesOfBlibli.add(new Employee(1004, 18, "zach2", "male", 1000.0,1));

        companies.get(0).setEmployees(employeesOfOOCL);
        companies.get(1).setEmployees(employeesOfBlibli);
        given(employeeRepository.findAll()).willReturn(companies);
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        List<CompanyRespond> foundCompanies = companyService.getCompanies();

//        then
        assertIterableEquals(companies.stream().map(CompanyMapper::map).collect(Collectors.toList()), foundCompanies);
    }

    @Test
    public void should_return_companies_when_get_companies_given_pages() throws NotFoundException {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        companies.add(new Company());
        Page<Company> companyPage = new PageImpl<>(companies);
        given(employeeRepository.findAll(any(Pageable.class))).willReturn(companyPage);
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        Page<CompanyRespond> foundCompanies = companyService.getCompanies(2, 1);
//        then
        assertIterableEquals(new PageImpl<>(companyPage.stream().map(CompanyMapper::map).collect(Collectors.toList())), foundCompanies);
    }


    @Test
    public void should_return_company_when_get_company_given_id() throws NotFoundException {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        Company company = new Company(1, "hello");
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(company));
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        CompanyRespond foundCompany = companyService.getCompany(1);
//        then
        assertEquals(CompanyMapper.map(company), foundCompany);
    }

    @Test
    public void should_return_employee_when_get_company_employee_given_id() throws NotFoundException {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        Company company = mock(Company.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(company));
        given(company.getEmployees()).willReturn(employees);
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        List<EmployeeResponse> employeesFound = companyService.getEmployees(1);

//        then
        assertEquals(employees.stream().map(EmployeeMapper::map).collect(Collectors.toList()), employeesFound);
    }


    @Test
    public void should_return_company_when_add_company_given_company() {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        Company company = new Company();
        given(employeeRepository.save(any(Company.class))).willReturn(company);
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        CompanyRespond companyFound = companyService.addCompany(new CompanyRequest(null, "huawei", 100, new ArrayList<>()));

//        then
        assertEquals(CompanyMapper.map(company), companyFound);
    }

    @Test
    public void should_return_company_when_update_company_given_company() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        Company company = new Company();
        given(companyRepository.save(any(Company.class))).willReturn(company);
        given(companyRepository.findById(anyInt())).willReturn(Optional.of(new Company(1, "s")));
//        when
        CompanyService companyService = new CompanyService(companyRepository);
        CompanyRespond companyFound = companyService.updateCompany(2, new CompanyRequest(33333, "huawei", 100, new ArrayList<>()));

//        then
        assertEquals(CompanyMapper.map(company), companyFound);
    }

    @Test
    public void should_return_company_when_delete_company_given_id() throws NotFoundException {
        //given
        CompanyRepository employeeRepository = mock(CompanyRepository.class);
        Company company = new Company();
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(company));
//        when
        CompanyService companyService = new CompanyService(employeeRepository);
        CompanyRespond companyFound = companyService.deleteCompany(1);

//        then
        assertEquals(CompanyMapper.map(company), companyFound);
    }
}
