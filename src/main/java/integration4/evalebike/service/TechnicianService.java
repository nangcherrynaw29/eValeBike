package integration4.evalebike.service;

import integration4.evalebike.domain.Technician;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.TechnicianRepository;
import integration4.evalebike.repository.TestBenchRepository;
import integration4.evalebike.repository.UserRepository;
import jakarta.transaction.Transactional;
import integration4.evalebike.utility.PasswordUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final PasswordUtility passwordUtility;
    private final TestBenchRepository testBenchRepository;
    private final UserRepository userRepository;

    public TechnicianService(TechnicianRepository technicianRepository, PasswordUtility passwordUtility, TestBenchRepository testBenchRepository, UserRepository userRepository) {
        this.technicianRepository = technicianRepository;
        this.passwordUtility = passwordUtility;
        this.testBenchRepository = testBenchRepository;
        this.userRepository = userRepository;
    }

    public List<Technician> getAll() {
        return technicianRepository.findByUserStatus(UserStatus.APPROVED);
    }

    public Technician getTechnicianById(Integer id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }

    public Technician saveTechnician(final String name, final String email, int createdBy) {
        String rawPassword = passwordUtility.generateRandomPassword(8);
        String hashedPassword = passwordUtility.hashPassword(rawPassword);

        Technician technician = new Technician(name, email);
        technician.setPassword(hashedPassword);
        technician.setCreatedBy(userRepository.findById(createdBy).orElseThrow(() -> NotFoundException.forAdmin(createdBy)));
        passwordUtility.sendPasswordEmail(email, rawPassword);
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
        testBenchRepository.deleteByTechnicianId(id);
        technicianRepository.delete(technician);
    }
}