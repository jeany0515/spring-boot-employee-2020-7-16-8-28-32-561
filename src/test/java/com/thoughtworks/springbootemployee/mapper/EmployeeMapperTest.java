package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeRespond;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Test
    void should_return_employee_when_mapper_given_employee_request() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest(1, 18, "alex", "male", 1000.0, 3);
        //when
        Employee employee = EmployeeMapper.map(employeeRequest);
        //then
        assertEquals(employee.getGender(), employeeRequest.getGender());
        assertEquals(employee.getId(), employeeRequest.getId());
        assertEquals(employee.getAge(), employeeRequest.getAge());
        assertEquals(employee.getCompanyId(), employeeRequest.getCompanyId());
        assertEquals(employee.getName(), employeeRequest.getName());
        assertEquals(employee.getName(), employeeRequest.getName());
    }

    @Test
    void should_return_employee_response_when_map_given_employee() {
        //given
        Employee employee = new Employee(1, 18, "alex", "male", 1000.0, 3);
        //when
        EmployeeRespond employeeRespond = EmployeeMapper.map(employee);
        //then
        assertEquals(employee.getGender(), employeeRespond.getGender());
        assertEquals(employee.getId(), employeeRespond.getId());
        assertEquals(employee.getAge(), employeeRespond.getAge());
        assertEquals(employee.getCompanyId(), employeeRespond.getCompanyId());
        assertEquals(employee.getName(), employeeRespond.getName());
        assertEquals(employee.getName(), employeeRespond.getName());
    }
}
