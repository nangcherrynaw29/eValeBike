package integration4.evalebike.controller.admin;

import integration4.evalebike.controller.admin.dto.TestBenchMapper;
import integration4.evalebike.controller.admin.dto.request.TechnicianRequestDTO;
import integration4.evalebike.controller.admin.dto.response.TechnicianResponseDTO;
import integration4.evalebike.controller.admin.dto.response.TestBenchResponseDTO;
import integration4.evalebike.controller.superAdmin.dto.PendingUserDto;
import integration4.evalebike.domain.*;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/technicians")
public class AdminApiController {
    private final TechnicianService technicianService;
    private final TestBenchService testBenchService;
    private final TestBenchMapper testBenchMapper;
    private final RecentActivityService recentActivityService;
    private final UserService userService;
    private final AdminService adminService;

    public AdminApiController(TechnicianService technicianService, TestBenchService testBenchService, TestBenchMapper testBenchMapper, RecentActivityService recentActivityService, UserService userService, AdminService adminService) {
        this.technicianService = technicianService;
        this.testBenchService = testBenchService;
        this.testBenchMapper = testBenchMapper;
        this.recentActivityService = recentActivityService;
        this.userService = userService;
        this.adminService = adminService;
    }

    // Create a new technician
    @PostMapping()
    public ResponseEntity<TechnicianResponseDTO> createTechnician(@RequestBody final TechnicianRequestDTO technicianDTO, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final Technician savedTechnician = technicianService.saveTechnician(technicianDTO.name(), technicianDTO.email(), userDetails.getUserId());
        recentActivityService.save(new RecentActivity(Activity.CREATED_USER, "Technician " + technicianDTO.name() + " created", LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TechnicianResponseDTO.toDto(savedTechnician));
    }

    // Retrieve all technicians
    @GetMapping()
    public ResponseEntity<List<TechnicianResponseDTO>> getAllTechnicians(@AuthenticationPrincipal final CustomUserDetails userDetails) {
        List<TechnicianResponseDTO> technicians = technicianService.getAll(userDetails)
                .stream()
                .map(TechnicianResponseDTO::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(technicians);
    }

    // Retrieve a specific technician by ID
    @GetMapping("/{id}")
    public ResponseEntity<TechnicianResponseDTO> getTechnicianById(@PathVariable Integer id) {
        Technician technician = technicianService.getTechnicianById(id);
        return ResponseEntity.ok(TechnicianResponseDTO.toDto(technician));
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
        Technician updatedTechnician = TechnicianRequestDTO.toEntity(technicianDTO);
        Technician savedTechnician = technicianService.updateTechnician(id, updatedTechnician);
        recentActivityService.save(new RecentActivity(Activity.UPDATED_USER, "Updated information about " + technicianDTO.name(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.ok(TechnicianResponseDTO.toDto(savedTechnician));
    }

    // Delete a technician
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Integer id, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        technicianService.deleteTechnician(id);
        recentActivityService.save(new RecentActivity(Activity.DELETED_USER, "Deleted technician with id: " + id, LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PendingUserDto>> getPendingApprovals(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Role currentRole = userDetails.getRole();
        List<PendingUserDto> pendingUsers = userService.getUsersByStatus(UserStatus.PENDING).stream()
                .filter(user -> {
                    Role targetRole = user.getRole();
                    return (targetRole == Role.TECHNICIAN && currentRole == Role.ADMIN);
                })
                .filter(user -> {
                    Company targetedCompany = user.getCompany();
                    return (Objects.equals(targetedCompany.getId(), userDetails.getCompany().getId()));
                })
                .map(PendingUserDto::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pendingUsers);
    }

    @GetMapping("/pending/count")
    public ResponseEntity<Integer> getPendingCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Role currentUserRole = userDetails.getRole();
        List<User> pendingUsers = userService.getUsersByStatus(UserStatus.PENDING);

        long count = pendingUsers.stream()
                .filter(user -> {
                    Role pendingRole = user.getRole();
                    return (pendingRole == Role.TECHNICIAN && currentUserRole == Role.ADMIN);
                })
                .filter(user -> {
                    User targetedCreator = user.getCreatedBy();
                    return (targetedCreator.getId() == userDetails.getUserId());
                })
                .count();

        return ResponseEntity.ok((int) count);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveUser(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserStatusAndNotify(id, UserStatus.APPROVED, userDetails.getUserId());
        return ResponseEntity.ok("User approved successfully.");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectUser(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserStatusAndNotify(id, UserStatus.REJECTED, userDetails.getUserId());
        return ResponseEntity.ok("User rejected successfully.");
    }

    @GetMapping("/filterAdmin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Administrator> filterAdmin(
            @RequestParam String type,
            @RequestParam String value) {

        // Call the service method based on the type to filter accordingly
        switch (type) {
            case "name":
                return adminService.filterAdminsByName(value);
            case "email":
                return adminService.filterAdminsByEmail(value);
            case "companyName":
                return adminService.filterAdminsByCompany(value);
            default:
                throw new IllegalArgumentException("Invalid filter type");
        }
    }



}