package com.thoughtworks.springbootemployee.respository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Optional<Company> findByName(String companyName);

    Company add(Company newCompany);
}
