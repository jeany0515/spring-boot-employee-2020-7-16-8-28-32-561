package com.thoughtworks.springbootemployee.dto;

public class EmployeeRequest {
    private Integer id;
    private Integer age;
    private String name;
    private String gender;
    private Double salary;
    private Integer companyId;

    public EmployeeRequest(Integer id, Integer age, String name, String gender, Double salary, Integer companyId) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public EmployeeRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
