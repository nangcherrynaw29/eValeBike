package integration4.evalebike.service;

import integration4.evalebike.domain.*;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.AdminRepository;
import integration4.evalebike.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordService passwordService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository, PasswordService passwordService, EmailService emailService, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public List<Administrator> getAllAdmins() {
        return adminRepository.findByUserStatus(UserStatus.APPROVED);
    }

    public Administrator getAdminById(final Integer id) {
        return adminRepository.findById(id).orElseThrow(() -> NotFoundException.forAdmin(id));
    }

    public Administrator saveAdmin(final String name, final String email, final Company company, int createdBy) {
        // Generate and hash the password using PasswordUtility
        String rawPassword = passwordService.generateRandomPassword(8);
        String hashedPassword = passwordService.hashPassword(rawPassword);

        // Create and save the Administrator
        final Administrator admin = new Administrator();
        admin.setName(name);
        admin.setEmail(email);
        admin.setCompany(company);
        admin.setPassword(hashedPassword);
        admin.setRole(Role.ADMIN);
        admin.setCreatedBy(userRepository.findById(createdBy).orElseThrow(() -> NotFoundException.forSuperAdmin(createdBy)));
        emailService.sendPasswordEmail(email, rawPassword);
        return adminRepository.save(admin);
    }

    public Administrator updateAdmin(Integer id, Administrator adminDetails) {
        Administrator existingAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        existingAdmin.setName(adminDetails.getName());
        existingAdmin.setEmail(adminDetails.getEmail());
//      existingAdmin.setRole(adminDetails.getRole());
        existingAdmin.setCompany(adminDetails.getCompany());
        return adminRepository.save(existingAdmin);
    }

    @Transactional
    public void deleteAdmin(Integer id) {
        Administrator admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));

        // Find all users created by this technician
        List<User> createdUsers = userRepository.findByCreatedById(id);
        if (!createdUsers.isEmpty()) {
            createdUsers.forEach(user -> user.setCreatedBy(null));
            userRepository.saveAll(createdUsers);
        }

        adminRepository.deleteById(id);
    }

    public List<Administrator> filterAdminsByName(String name) {
        return adminRepository.findByNameContainingIgnoreCase(name); // Adjust to your repo method
    }

    public List<Administrator> filterAdminsByEmail(String email) {
        return adminRepository.findByEmailContainingIgnoreCase(email); // Adjust to your repo method
    }

    public List<Administrator> filterAdminsByCompany(String companyName) {
        return adminRepository.findByCompanyNameContainingIgnoreCase(companyName); // Adjust to your repo method
    }
}