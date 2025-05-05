package integration4.evalebike.service;

import integration4.evalebike.domain.Technician;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.TechnicianRepository;
import integration4.evalebike.repository.TestBenchRepository;
import jakarta.transaction.Transactional;
import integration4.evalebike.utility.PasswordUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final PasswordUtility passwordUtility;
    private final TestBenchRepository testBenchRepository;

    public TechnicianService(TechnicianRepository technicianRepository, PasswordUtility passwordUtility, TestBenchRepository testBenchRepository) {
        this.technicianRepository = technicianRepository;
        this.passwordUtility = passwordUtility;
        this.testBenchRepository = testBenchRepository;
    }

    public List<Technician> getAll() {
        return technicianRepository.findAll();
    }

    public Technician getTechnicianById(Integer id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }

    public Technician saveTechnician(final String name, final String email) {
        String rawPassword = passwordUtility.generateRandomPassword(8);
        String hashedPassword = passwordUtility.hashPassword(rawPassword);

        Technician technician = new Technician(name, email);
        technician.setPassword(hashedPassword);
        passwordUtility.sendPasswordEmail(email, rawPassword);
        return technicianRepository.save(technician);
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