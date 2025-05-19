package integration4.evalebike.service;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.Company;
import integration4.evalebike.domain.Role;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.AdminRepository;
import integration4.evalebike.repository.UserRepository;
import integration4.evalebike.utility.PasswordUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordUtility passwordUtility;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository, PasswordUtility passwordUtility, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.passwordUtility = passwordUtility;
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
        String rawPassword = passwordUtility.generateRandomPassword(8);
        String hashedPassword = passwordUtility.hashPassword(rawPassword);

        // Create and save the Administrator
        final Administrator admin = new Administrator();
        admin.setName(name);
        admin.setEmail(email);
        admin.setCompany(company);
        admin.setPassword(hashedPassword);
        admin.setRole(Role.ADMIN);
        admin.setCreatedBy(userRepository.findById(createdBy).orElseThrow(() -> NotFoundException.forSuperAdmin(createdBy)));
        passwordUtility.sendPasswordEmail(email, rawPassword);
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

    public void deleteAdmin(Integer id) {
        Administrator admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
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