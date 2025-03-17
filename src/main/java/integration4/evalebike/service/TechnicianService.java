package integration4.evalebike.service;

import integration4.evalebike.domain.Technician;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;

    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public List<Technician> getAll() {
        return technicianRepository.findAll();
    }

    public Technician getTechnicianById(Integer id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }

    public Technician saveTechnician(final String name, final String email, final String password) {
        Technician technician = new Technician();
        technician.setName(name);
        technician.setEmail(email);
        technician.setPassword(password);
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

    public void deleteTechnician(Integer id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
        technicianRepository.delete(technician);
    }
}
