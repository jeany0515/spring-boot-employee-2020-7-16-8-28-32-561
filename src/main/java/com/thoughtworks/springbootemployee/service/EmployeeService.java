package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    public static final String EMPLOYEE_NOT_FOUND = "employee not found";
    public static final String NO_EMPLOYEE = "no employee";
    public static final String NO_QUALIFIED_EMPLOYEES = "no qualified employees";
    public static final String COMPANY_NOT_FOUND = "company not found";
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

    public List<EmployeeResponse> getEmployees() throws NotFoundException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return employees.stream().map(EmployeeMapper::map).collect(Collectors.toList());
    }

    public Page<EmployeeResponse> getEmployees(Integer page, Integer pageSize) throws NotFoundException {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return new PageImpl<>(employees.stream().map(EmployeeMapper::map).collect(Collectors.toList()), employees.getPageable(), employees.getTotalElements());
    }

    public List<EmployeeResponse> getEmployees(String gender) throws NotFoundException {
        List<Employee> employees = employeeRepository.findAllByGender(gender);
        if (employees.isEmpty()) {
            throw new NotFoundException(NO_QUALIFIED_EMPLOYEES);
        }
        return employees.stream().map(EmployeeMapper::map).collect(Collectors.toList());
    }

    public EmployeeResponse getEmployee(Integer id) throws NotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new NotFoundException(EMPLOYEE_NOT_FOUND);
        }
        return EmployeeMapper.map(employee);
    }

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) throws NotFoundException {
        Employee employee = EmployeeMapper.map(employeeRequest);
        if (employee.getCompanyId() != null) {
            companyService.getCompany(employee.getCompanyId());
        }
        return EmployeeMapper.map(employeeRepository.save(employee));
    }

    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest employeeRequest) throws NotFoundException {
        if (employeeRequest.getCompanyId() == null) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        companyService.getCompany(employeeRequest.getCompanyId());
        getEmployee(id);
        Employee employee = EmployeeMapper.map(employeeRequest);
        employee.setId(id);
        return EmployeeMapper.map(employeeRepository.save(employee));
    }

    public EmployeeResponse delete(Integer id) throws NotFoundException {
        EmployeeResponse employee = getEmployee(id);
        employeeRepository.deleteById(id);
        return employee;
    }
}
