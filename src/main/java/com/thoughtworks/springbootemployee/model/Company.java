package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private Integer id;
    private String companyName;
    private List<Employee> employees;
    private Integer empoyeesNumber;

    public Company(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        this.empoyeesNumber = this.employees.size();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
