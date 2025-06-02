package integration4.evalebike.service;

import integration4.evalebike.domain.Company;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public long countAllCompanies() {
        return companyRepository.count();
    }

    public Company getById(int id) {
        return companyRepository.findById(id).orElseThrow(() -> NotFoundException.forCompany(id));
    }
}