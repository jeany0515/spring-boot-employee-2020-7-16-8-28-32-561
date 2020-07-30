package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

public class EmployeeMapperTest {
    @Test
    void should_return_employee_when_mapper_given_employee_request() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeMapper employeeMapper = new EmployeeMapper();
        //when
        Employee employee = employeeMapper.mapper(employeeRequest);
        //then
        assertEquals(employee.getGender(), employeeRequest.getGender());
        assertEquals(employee.getId(), employeeRequest.getId());
        assertEquals(employee.getAge(), employeeRequest.getAge());
        assertEquals(employee.getCompanyId(), employeeRequest.getCompanyId());
        assertEquals(employee.getName(), employeeRequest.getName());
        assertEquals(employee.getName(), employeeRequest.getName());
    }
}
