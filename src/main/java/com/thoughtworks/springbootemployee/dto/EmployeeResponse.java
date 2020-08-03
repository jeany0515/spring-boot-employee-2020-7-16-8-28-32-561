package com.thoughtworks.springbootemployee.dto;

import java.util.Objects;

public class EmployeeResponse {
    private Integer id;
    private Integer age;
    private String name;
    private String gender;
    private Double salary;
    private Integer companyId;

    public EmployeeResponse(Integer id, Integer age, String name, String gender, Double salary, Integer companyId) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public EmployeeResponse() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeResponse that = (EmployeeResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(age, that.age) &&
                Objects.equals(name, that.name) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, name, gender, salary, companyId);
    }
}
