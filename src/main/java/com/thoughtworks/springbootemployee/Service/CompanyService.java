package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;

public class CompanyService {
    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository mockedCompanyRepository) {
        this.companyRepository=mockedCompanyRepository;
    }

    public Company update(Company newCompany) {
        return null;
    }
}
