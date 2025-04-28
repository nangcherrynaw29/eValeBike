package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.AddAdminDto;
import integration4.evalebike.controller.superAdmin.dto.AdministratorDto;
import integration4.evalebike.domain.Activity;
import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.RecentActivity;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.RecentActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/super-admin/admins")
public class SuperAdminApiController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final RecentActivityService recentActivityService;


    public SuperAdminApiController(AdminService adminService, AdminMapper adminMapper, RecentActivityService recentActivityService) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.recentActivityService = recentActivityService;
    }

    //  A list of administrator
    @GetMapping()
    public ResponseEntity<List<AdministratorDto>> getAllAdmins() {
        final List<AdministratorDto> administratorDtos = adminService.getAllAdmins()
                .stream().map(adminMapper :: toAdminDto)
                .toList();
        return ResponseEntity.ok(administratorDtos);
    }

    @PostMapping()
    public ResponseEntity<AdministratorDto> createAdmin(@RequestBody final AddAdminDto addAdminDto, @AuthenticationPrincipal final CustomUserDetails userDetails) {
      final Administrator administrator = adminService.saveAdmin(
              addAdminDto.name(),
              addAdminDto.email(),
              addAdminDto.companyName());
      recentActivityService.save(new RecentActivity(Activity.CREATED_USER, "Created admin " + addAdminDto.name(), LocalDateTime.now(), userDetails.getUserId()));
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(adminMapper.toAdminDto(administrator));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer adminId, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        adminService.deleteAdmin(adminId);
        recentActivityService.save(new RecentActivity(Activity.DELETED_USER, "Deleted admin with id: " + adminId, LocalDateTime.now(), userDetails.getUserId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
