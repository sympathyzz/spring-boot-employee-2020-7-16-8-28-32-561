package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public Company findByName(String companyName) {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().filter(company -> company.getCompanyName().equals(companyName)).findFirst().orElse(null);
    }

    public List<Company> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public String deleteCompanyByName(String companyName) {
        List<Company> companies = companyRepository.findAll();
        Company specifiedCompany=findByName(companyName);
        if (specifiedCompany!=null) {
            if(companies.remove(specifiedCompany )){
                return "delete success!";
            }
        }
        return "delete fail!";
    }
}
