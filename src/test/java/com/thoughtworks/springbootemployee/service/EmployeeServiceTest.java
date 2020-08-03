package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyRespond;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {

    @Test
    public void should_return_employees_when_get_employees_given_none() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, 18, "hello", "male", 1000.0,1));
        employees.add(new Employee(2, 18, "hellome", "male", 1000.0,1));
        given(employeeRepository.findAll()).willReturn(employees);
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<EmployeeResponse> employeeList = employeeService.getEmployees();
        //then
        assertIterableEquals(employeeList, employees.stream().map(EmployeeMapper::map).collect(Collectors.toList()));
    }

    @Test
    void should_return_employees_when_get_employees_given_page_pageSize() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());
        Page<Employee> employees = new PageImpl<>(employeeList);
        given(employeeRepository.findAll(any(Pageable.class))).willReturn(employees);
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Page<EmployeeResponse> employeesFound = employeeService.getEmployees(1, 2);
        //then
        assertEquals(new PageImpl<>(employees.stream().map(EmployeeMapper::map).collect(Collectors.toList())), employeesFound);
    }

    @Test
    void should_return_employees_when_get_employees_given_gender() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, 18, "hello", "male", 1000.0,1));
        given(employeeRepository.findAllByGender(anyString())).willReturn(employees);
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<EmployeeResponse> employeeList = employeeService.getEmployees("male");
        //then
        assertIterableEquals(employees.stream().map(EmployeeMapper::map).collect(Collectors.toList()), employeeList);
    }

    @Test
    void should_return_employee_when_get_employee_given_id() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, 18, "hello", "male", 1000.0,1);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(employee));
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        EmployeeResponse employeeFound = employeeService.getEmployee(1);
        //then
        assertEquals(EmployeeMapper.map(employee), employeeFound);
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        CompanyService companyService = mock(CompanyService.class);
        EmployeeRequest employee = new EmployeeRequest(23, 18, "alex", "male", 121341564.0, 1);
        given(employeeRepository.save(any(Employee.class))).willReturn(EmployeeMapper.map(employee));
        given(companyService.getCompany(anyInt())).willReturn(new CompanyRespond());
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.setCompanyService(companyService);
        //when
        EmployeeResponse employeeFound = employeeService.addEmployee(employee);
        //then
        assertEquals(EmployeeMapper.map(EmployeeMapper.map(employee)), employeeFound);
    }

    @Test
    void should_return_employee_when_update_employee_given_employee() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        CompanyService companyService = mock(CompanyService.class);
        EmployeeRequest employee = new EmployeeRequest(23, 18, "alex", "female", 1000.0, 1);
        given(employeeRepository.save(any(Employee.class))).willReturn(EmployeeMapper.map(employee));
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(new Employee(1, 1, "ff", "ff", 1.0, 1)));
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.setCompanyService(companyService);
        given(companyService.getCompany(anyInt())).willReturn(new CompanyRespond(1, "ali", 100, null));
        //when
        EmployeeResponse employeeFound = employeeService.updateEmployee(1, employee);
        //then
        assertEquals(EmployeeMapper.map(EmployeeMapper.map(employee)), employeeFound);
    }

    @Test
    void should_return_employee_when_delete_employee_given_employee_id() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(23, 18, "alex", "female", 1000.0,1);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(employee));
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        EmployeeResponse employeeFound = employeeService.delete(1);
        //then
        assertEquals(EmployeeMapper.map(employee), employeeFound);
    }
}
