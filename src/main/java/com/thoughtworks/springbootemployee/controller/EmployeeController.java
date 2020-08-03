package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.ResultBean;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
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
    public final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<List<EmployeeResponse>> getEmployees(@PathParam("page") Integer page, @PathParam("pageSize") Integer pageSize, @PathParam("gender") String gender) throws NotFoundException {
        List<EmployeeResponse> result = gender == null ? null : employeeService.getEmployees(gender);
        if (result == null) {
            result = employeeService.getEmployees();
        }
        return ResultBean.success((page == null || pageSize == null) ? result : employeeService.getEmployees(page - 1, pageSize).getContent());
    }

    @GetMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<EmployeeResponse> getEmployee(@PathVariable Integer employeeID) throws NotFoundException {
        return ResultBean.success(employeeService.getEmployee(employeeID));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultBean<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest) throws NotFoundException {
        return ResultBean.success(employeeService.addEmployee(employeeRequest));
    }

    @PutMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<EmployeeResponse> updateEmployee(@PathVariable Integer employeeID, @RequestBody EmployeeRequest employeeRequest) throws NotFoundException {
        return ResultBean.success(employeeService.updateEmployee(employeeID, employeeRequest));
    }

    @DeleteMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<Boolean> deleteEmployee(@PathVariable Integer employeeID) throws NotFoundException {
        employeeService.delete(employeeID);
        return ResultBean.success();
    }

}
