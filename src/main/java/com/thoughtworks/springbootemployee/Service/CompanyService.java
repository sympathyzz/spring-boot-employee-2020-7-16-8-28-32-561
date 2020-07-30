package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository mockedCompanyRepository) {
        this.companyRepository=mockedCompanyRepository;
    }

    public Company update(Integer companyId,Company newCompany) {
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            throw new NoSuchDataException();
        }
        if (newCompany.getCompanyName()!=null){
            company.get().setCompanyName(newCompany.getCompanyName());
        }
        if (newCompany.getEmployeesNumber()!=null){
            company.get().setEmployeesNumber(newCompany.getEmployeesNumber());
        }
        if (newCompany.getEmployees()!=null){
            company.get().setEmployees(newCompany.getEmployees());
        }
        companyRepository.save(company.get());
        return company.get();
    }

    public Company findById(Integer companyId) {
        return companyRepository.findById(companyId).get();
    }

    public List<Company> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    public Page<Company> getCompanyByPage(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page,pageSize));
    }


    public Boolean deleteCompanyById(Integer companyId) {
        Optional<Company> company=companyRepository.findById(companyId);
        company.ifPresent(companyRepository::delete);
        if(companyRepository.findById(companyId)==null){
            return false;
        }
        return true;
    }

    public Company addCompany(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId){
        return findById(companyId).getEmployees();
    }
}
