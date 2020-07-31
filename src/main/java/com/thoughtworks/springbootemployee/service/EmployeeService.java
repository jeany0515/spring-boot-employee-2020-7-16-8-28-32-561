package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    public static final String EMPLOYEE_NOT_FOUND = "employee not found";
    public static final String NO_EMPLOYEE = "no employee";
    public static final String NO_QUALIFIED_EMPLOYEES = "no qualified employees";
    private final EmployeeRepository employeeRepository;
    private CompanyService companyService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public List<Employee> getEmployees() throws NotFoundException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return employees;
    }

    public Page<Employee> getEmployees(Integer page, Integer pageSize) throws NotFoundException {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return employees;
    }

    public List<Employee> getEmployees(String gender) throws NotFoundException {
        List<Employee> employees = employeeRepository.findAllByGender(gender);
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_QUALIFIED_EMPLOYEES);
        }
        return employees;
    }

    public Employee getEmployee(Integer id) throws NotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new NotFoundException(EMPLOYEE_NOT_FOUND);
        }
        return employee;
    }

    public Employee addEmployee(Employee employee) throws NotFoundException {
        if (employee.getCompanyId() != null) {
            companyService.getCompany(employee.getCompanyId());
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        updatedEmployee.setId(id);
        return employeeRepository.save(updatedEmployee);
    }

    public Employee delete(Integer id) throws NotFoundException {
        Employee employee = getEmployee(id);
        employeeRepository.deleteById(id);
        return employee;
    }
}
