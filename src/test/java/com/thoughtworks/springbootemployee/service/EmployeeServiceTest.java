package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Employee> employeeList = employeeService.getEmployees();
        //then
        assertIterableEquals(employeeList, employees);
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
        Page<Employee> employeesFound = employeeService.getEmployees(1, 2);
        //then
        assertEquals(employees, employeesFound);
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
        List<Employee> employeeList = employeeService.getEmployees("male");
        //then
        assertIterableEquals(employeeList, employeeList);
    }

    @Test
    void should_return_employee_when_get_employee_given_id() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, 18, "hello", "male", 1000.0,1);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(employee));
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employeeFound = employeeService.getEmployee(1);
        //then
        assertEquals(employee, employeeFound);
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(23, 18, "alex", "male", 121341564.0,1);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employeeFound = employeeService.addEmployee(employee);
        //then
        assertEquals(employee, employeeFound);
    }

    @Test
    void should_return_employee_when_update_employee_given_employee() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(23, 18, "alex", "female", 1000.0,1);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employeeFound = employeeService.updateEmployee(1, employee);
        //then
        assertEquals(employee, employeeFound);
    }

    @Test
    void should_return_employee_when_delete_employee_given_employee_id() throws NotFoundException {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(23, 18, "alex", "female", 1000.0,1);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.of(employee));
        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employeeFound = employeeService.delete(1);
        //then
        assertEquals(employee, employeeFound);
    }
}
