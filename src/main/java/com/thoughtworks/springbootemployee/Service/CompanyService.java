package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;

import java.util.List;

public class CompanyService {
    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository mockedCompanyRepository) {
        this.companyRepository=mockedCompanyRepository;
    }

    public Company update(Company newCompany) {
        CompanyController companyController = new CompanyController();
        List<Company> companies = companyController.getAllData();
        for (Company company : companies) {
            if (newCompany.getId() == company.getId()) {
                company.setCompanyName(newCompany.getCompanyName());
                company.setEmployeesNumber(newCompany.getEmployeesNumber());
                company.setEmployees(newCompany.getEmployees());
                return company;
            }
        }
        return null;
    }
}
