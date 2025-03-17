package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.AddAdminDto;
import integration4.evalebike.controller.superAdmin.dto.AdministratorDto;
import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.SuperAdmin;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.SuperAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
public class SuperAdminApiController {
    private SuperAdminService superAdminService;
    private AdminService adminService;
    private final AdminMapper adminMapper;


    public SuperAdminApiController(AdminService adminService, SuperAdminService superAdminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.superAdminService = superAdminService;
        this.adminMapper = adminMapper;
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
    public ResponseEntity<AdministratorDto> createAdmin(@RequestBody final AddAdminDto addAdminDto) {
      final Administrator administrator = adminService.saveAdmin(
              addAdminDto.name(),
              addAdminDto.email(),
              addAdminDto.companyName());
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(adminMapper.toAdminDto(administrator));
    }

    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer adminId) {
        adminService.deleteAdmin(adminId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
