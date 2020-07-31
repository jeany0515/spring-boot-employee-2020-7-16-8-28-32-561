package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.ResultBean;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    public static final String NO_COMPANY_FOUND = "no company found";
    public static final String COMPANY_NOT_FOUND = "company not found";
    public static final String NO_EMPLOYEE = "no employee";
    private static final String ID_COULD_NOT_BE_SET = "ID could not be set";
    private final CompanyRepository companyRepository;

    @Autowired
    CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public List<Company> getCompanies() throws NotFoundException {
        List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            throw new NotFoundException(NO_COMPANY_FOUND);
        }
        return companies;
    }

    public Page<Company> getCompanies(Integer page, Integer pageSize) throws NotFoundException {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, pageSize));
        if (companies.isEmpty()) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        return companies;
    }

    public Company getCompany(Integer id) throws NotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        return company.get();
    }

    public List<Employee> getEmployees(Integer id) throws NotFoundException {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        if (company.getEmployees().isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return company.getEmployees();
    }

    public Company addCompany(Company company) {
        Assert.isNull(company.getId(), ID_COULD_NOT_BE_SET);
        return companyRepository.save(company);
    }

    public Company updateCompany(Integer id, Company updatedCompany) {
        updatedCompany.setId(id);
        return companyRepository.save(updatedCompany);
    }

    public Company deleteCompany(Integer id) throws NotFoundException {
        Company company = getCompany(id);
        companyRepository.deleteById(id);
        return company;
    }
}
