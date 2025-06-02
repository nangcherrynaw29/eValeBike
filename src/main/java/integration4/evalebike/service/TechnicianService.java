package integration4.evalebike.service;

import integration4.evalebike.domain.*;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.CompanyRepository;
import integration4.evalebike.repository.TechnicianRepository;
import integration4.evalebike.repository.TestBenchRepository;
import integration4.evalebike.repository.UserRepository;
import integration4.evalebike.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final PasswordService passwordService;
    private final EmailService emailService;
    private final TestBenchRepository testBenchRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public TechnicianService(TechnicianRepository technicianRepository, PasswordService passwordService, EmailService emailService, TestBenchRepository testBenchRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        this.technicianRepository = technicianRepository;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.companyRepository = companyRepository;
        this.testBenchRepository = testBenchRepository;
        this.userRepository = userRepository;
    }

    public List<Technician> getAll(CustomUserDetails currentUser) {
        if (currentUser.getRole() == Role.SUPER_ADMIN) {
            return technicianRepository.findByUserStatus(UserStatus.APPROVED);
        } else {
            return technicianRepository.findByUserStatusAndCompany(
                    UserStatus.APPROVED,
                    currentUser.getCompany()
            );
        }
    }

    public Technician getTechnicianById(Integer id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }

    public Technician saveTechnician(final String name, final String email, int createdBy, Integer companyId) {
        String rawPassword = passwordService.generateRandomPassword(8);
        String hashedPassword = passwordService.hashPassword(rawPassword);

        Technician technician = new Technician(name, email);
        technician.setPassword(hashedPassword);
        technician.setCreatedBy(userRepository.findById(createdBy).orElseThrow(() -> NotFoundException.forAdmin(createdBy)));
        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElse(null);
            technician.setCompany(company);
        } else {
            technician.setCompany(userRepository.findById(createdBy).orElseThrow((() -> NotFoundException.forTechnician(createdBy))).getCompany());
        }
        emailService.sendPasswordEmail(email, rawPassword);
        return technicianRepository.save(technician);
    }

    public List<Technician> getFilteredTechnicians(String name, String email) {
        return technicianRepository.findByFilters(name, email);
    }

    public Technician updateTechnician(Integer id, Technician updatedTechnician) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
        technician.setName(updatedTechnician.getName());
        technician.setEmail(updatedTechnician.getEmail());
        technician.setPassword(updatedTechnician.getPassword());
        return technicianRepository.save(technician);
    }

    @Transactional
    public void deleteTechnician(Integer id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));

        // Find all users created by this technician
        List<User> createdUsers = userRepository.findByCreatedById(id);
        if (!createdUsers.isEmpty()) {
            createdUsers.forEach(user -> user.setCreatedBy(null));
            userRepository.saveAll(createdUsers);
        }

        technicianRepository.delete(technician);
        userRepository.deleteById(id);
    }
}