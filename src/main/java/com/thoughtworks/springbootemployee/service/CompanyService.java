package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyRespond;
import com.thoughtworks.springbootemployee.dto.EmployeeRespond;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<CompanyRespond> getCompanies() throws NotFoundException {
        List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            throw new NotFoundException(NO_COMPANY_FOUND);
        }
        return companies.stream().map(CompanyMapper::map).collect(Collectors.toList());
    }

    public Page<CompanyRespond> getCompanies(Integer page, Integer pageSize) throws NotFoundException {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, pageSize));
        if (companies.isEmpty()) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        return new PageImpl<>(companies.stream().map(CompanyMapper::map).collect(Collectors.toList()));
    }

    public CompanyRespond getCompany(Integer id) throws NotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        return CompanyMapper.map(company.get());
    }

    public List<EmployeeRespond> getEmployees(Integer id) throws NotFoundException {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new NotFoundException(COMPANY_NOT_FOUND);
        }
        if (company.getEmployees().isEmpty()) {
            throw new NotFoundException(NO_EMPLOYEE);
        }
        return company.getEmployees().stream().map(EmployeeMapper::map).collect(Collectors.toList());
    }

    public CompanyRespond addCompany(CompanyRequest companyRequest) {
        Company company = CompanyMapper.map(companyRequest);
        Assert.isNull(company.getId(), ID_COULD_NOT_BE_SET);
        return CompanyMapper.map(companyRepository.save(company));
    }

    public CompanyRespond updateCompany(Integer id, CompanyRequest companyRequest) throws NotFoundException {
        Company company = CompanyMapper.map(companyRequest);
        getCompany(id);
        company.setId(id);
        return CompanyMapper.map(companyRepository.save(company));
    }

    public CompanyRespond deleteCompany(Integer id) throws NotFoundException {
        CompanyRespond company = getCompany(id);
        companyRepository.deleteById(id);
        return company;
    }
}
