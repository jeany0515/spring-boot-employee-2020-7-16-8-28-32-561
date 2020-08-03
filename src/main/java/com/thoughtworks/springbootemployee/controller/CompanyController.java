package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyRespond;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.ResultBean;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author XUAL7
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<List<CompanyRespond>> getCompanies(@PathParam("page") Integer page, @PathParam("pageSize") Integer pageSize) throws NotFoundException {
        if (page == null || pageSize == null) {
            return ResultBean.success(companyService.getCompanies());
        }
        //Count at 0 at database
        return ResultBean.success(companyService.getCompanies(page - 1, pageSize).getContent());
    }

    @GetMapping("/{companyID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<CompanyRespond> getCompany(@PathVariable Integer companyID) throws NotFoundException {
        return ResultBean.success(companyService.getCompany(companyID));
    }

    @GetMapping("/{companyID}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<List<EmployeeResponse>> getEmployee(@PathVariable Integer companyID) throws NotFoundException {
        return ResultBean.success(companyService.getEmployees(companyID));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultBean<CompanyRespond> addCompany(@RequestBody CompanyRequest companyRequest) {
        return ResultBean.success(companyService.addCompany(companyRequest));
    }

    @PutMapping("/{companyID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<CompanyRespond> updateCompany(@RequestBody CompanyRequest companyInfo, @PathVariable Integer companyID) {
        return ResultBean.success(companyService.updateCompany(companyID, companyInfo));
    }

    @DeleteMapping("/{companyID}")
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<Boolean> deleteCompany(@PathVariable Integer companyID) throws NotFoundException {
        companyService.deleteCompany(companyID);
        return ResultBean.success();
    }
}
