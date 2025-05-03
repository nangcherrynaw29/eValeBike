package integration4.evalebike.controller.admin;

import integration4.evalebike.controller.admin.dto.TechnicianMapper;
import integration4.evalebike.controller.admin.dto.TestBenchMapper;
import integration4.evalebike.controller.admin.dto.request.TechnicianRequestDTO;
import integration4.evalebike.controller.admin.dto.response.TechnicianResponseDTO;
import integration4.evalebike.controller.admin.dto.response.TestBenchResponseDTO;
import integration4.evalebike.domain.Activity;
import integration4.evalebike.domain.RecentActivity;
import integration4.evalebike.domain.Technician;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.RecentActivityService;
import integration4.evalebike.service.TechnicianService;
import integration4.evalebike.service.TestBenchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/technicians")
public class AdminApiController {
    private final TechnicianService technicianService;
    private final TechnicianMapper technicianMapper;
    private final TestBenchService testBenchService;
    private final TestBenchMapper testBenchMapper;
    private final RecentActivityService recentActivityService;

    public AdminApiController(TechnicianService technicianService, TechnicianMapper technicianMapper, TestBenchService testBenchService, TestBenchMapper testBenchMapper, RecentActivityService recentActivityService) {
        this.technicianService = technicianService;
        this.technicianMapper = technicianMapper;
        this.testBenchService = testBenchService;
        this.testBenchMapper = testBenchMapper;
        this.recentActivityService = recentActivityService;
    }

    // Create a new technician
    @PostMapping()
    public ResponseEntity<TechnicianResponseDTO> createTechnician(@RequestBody final TechnicianRequestDTO technicianDTO, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final Technician savedTechnician = technicianService.saveTechnician(technicianDTO.name(), technicianDTO.email());
        recentActivityService.save(new RecentActivity(Activity.CREATED_USER, "Technician " + technicianDTO.name() + " created", LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(technicianMapper.toDto(savedTechnician));
    }

    // Retrieve all technicians
    @GetMapping()
    public ResponseEntity<List<TechnicianResponseDTO>> getAllTechnicians() {
        List<TechnicianResponseDTO> technicians = technicianService.getAll()
                .stream()
                .map(technicianMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(technicians);
    }

    // Retrieve a specific technician by ID
    @GetMapping("/{id}")
    public ResponseEntity<TechnicianResponseDTO> getTechnicianById(@PathVariable Integer id) {
        Technician technician = technicianService.getTechnicianById(id);
        return ResponseEntity.ok(technicianMapper.toDto(technician));
    }

    // Retrieve all test benches
    @GetMapping("/test-benches")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TestBenchResponseDTO>> getAllTestBenches() {
        List<TestBenchResponseDTO> testBenches = testBenchService.getAllTestBenches()
                .stream()
                .map(testBenchMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(testBenches);
    }

    // Update an existing technician
    @PatchMapping("/{id}")
    public ResponseEntity<TechnicianResponseDTO> updateTechnician(@PathVariable Integer id, @RequestBody TechnicianRequestDTO technicianDTO, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        Technician updatedTechnician = technicianMapper.toEntity(technicianDTO);
        Technician savedTechnician = technicianService.updateTechnician(id, updatedTechnician);
        recentActivityService.save(new RecentActivity(Activity.UPDATED_USER, "Updated information about " + technicianDTO.name(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.ok(technicianMapper.toDto(savedTechnician));
    }

    // Delete a technician
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Integer id, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        technicianService.deleteTechnician(id);
        recentActivityService.save(new RecentActivity(Activity.DELETED_USER, "Deleted technician with id: " + id, LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.noContent().build();
    }
}