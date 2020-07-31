package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.ResultBean;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author XUAL7
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    public static final String NOT_EXIST = "not exist";
    public final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<List<Employee>> getEmployees(@PathParam("page") Integer page, @PathParam("pageSize") Integer pageSize, @PathParam("gender") String gender) throws NotFoundException {
        List<Employee> result = gender == null ? null : employeeService.getEmployees(gender);
        if (result == null) {
            result = employeeService.getEmployees();
        }
        return ResultBean.success((page == null || pageSize == null) ? result : employeeService.getEmployees(page - 1, pageSize).getContent());
    }

    @GetMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<Employee> getEmployee(@PathVariable Integer employeeID) throws NotFoundException {
        return ResultBean.success(employeeService.getEmployee(employeeID));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultBean<Employee> addEmployee(@RequestBody Employee employee) throws NotFoundException {
        return ResultBean.success(employeeService.addEmployee(employee));
    }

    @PutMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<Employee> updateEmployee(@PathVariable Integer employeeID, @RequestBody Employee employee) {
        return ResultBean.success(employeeService.updateEmployee(employeeID, employee));
    }

    @DeleteMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<Boolean> deleteEmployee(@PathVariable Integer employeeID) throws NotFoundException {
        employeeService.delete(employeeID);
        return ResultBean.success();
    }

}
