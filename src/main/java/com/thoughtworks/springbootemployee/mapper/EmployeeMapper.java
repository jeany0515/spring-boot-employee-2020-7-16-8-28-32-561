package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeRespond;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {
    public static Employee map(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public static EmployeeRespond map(Employee employee) {
        EmployeeRespond employeeRespond = new EmployeeRespond();
        BeanUtils.copyProperties(employee, employeeRespond);
        return employeeRespond;
    }
}
