package integration4.evalebike.service;
import integration4.evalebike.domain.SuperAdmin;
import integration4.evalebike.exceptions.NotFoundException;
import integration4.evalebike.repository.SuperAdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
public class SuperAdminService {
    private SuperAdminRepository superAdminRepository;

    public SuperAdminService(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }

    // Get all admins
    public List<SuperAdmin> getAllSuperAdmins() {
        return superAdminRepository.findAll();
    }

    // Get admin by ID
    public SuperAdmin getSuperAdminById(final Integer id) {
            return superAdminRepository.findById(id).orElseThrow(()-> NotFoundException.admin(id));
        }

    // Save a new admin
    public SuperAdmin saveSuperAdmin(SuperAdmin superAdminadmin) {
        return superAdminRepository.save(superAdminadmin);
    }

    // Update a superadmin
    public SuperAdmin updateSuperAdmin(Integer id, SuperAdmin adminDetails) {
        SuperAdmin existingSuperAdmin = superAdminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        existingSuperAdmin.setName(adminDetails.getName());
        existingSuperAdmin.setEmail(adminDetails.getEmail());
        existingSuperAdmin.setCompanyName(adminDetails.getCompanyName());
        return superAdminRepository.save(existingSuperAdmin);
    }


}
