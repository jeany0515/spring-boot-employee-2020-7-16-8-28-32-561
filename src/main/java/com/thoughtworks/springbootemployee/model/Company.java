package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Company(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Company() {
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        this.employeesNumber = this.employees.size();
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
