package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.AddAdminDto;
import integration4.evalebike.controller.superAdmin.dto.AdministratorDto;
import integration4.evalebike.controller.superAdmin.dto.PendingUserDto;
import integration4.evalebike.domain.*;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.RecentActivityService;
import integration4.evalebike.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/super-admin/admins")
public class SuperAdminApiController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final RecentActivityService recentActivityService;
    private final UserService userService;

    public SuperAdminApiController(AdminService adminService, AdminMapper adminMapper, RecentActivityService recentActivityService, UserService userService) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.recentActivityService = recentActivityService;
        this.userService = userService;
    }

    //  A list of administrator
    @GetMapping()
    public ResponseEntity<List<AdministratorDto>> getAllAdmins() {
        final List<AdministratorDto> administratorDtos = adminService.getAllAdmins()
                .stream().map(adminMapper::toAdminDto)
                .toList();
        return ResponseEntity.ok(administratorDtos);
    }

    @PostMapping()
    public ResponseEntity<AdministratorDto> createAdmin(@RequestBody final AddAdminDto addAdminDto, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        final Administrator administrator = adminService.saveAdmin(
                addAdminDto.name(),
                addAdminDto.email(),
                addAdminDto.companyName(),
                userDetails.getUserId());
        recentActivityService.save(new RecentActivity(Activity.PENDING_APPROVAL, "There is a new admin waiting for an approval: " + administrator.getEmail(), LocalDateTime.now(), userDetails.getUserId()));
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

    @GetMapping("/pending")
    public ResponseEntity<List<PendingUserDto>> getPendingApprovals(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Role currentRole = userDetails.getRole();
        List<PendingUserDto> pendingUsers = userService.getUsersByStatus(UserStatus.PENDING).stream()
                .filter(user -> {
                    Role targetRole = user.getRole();
                    return (targetRole == Role.ADMIN && currentRole == Role.SUPER_ADMIN);
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
                    return (pendingRole == Role.ADMIN && currentUserRole == Role.SUPER_ADMIN);
                })
                .count();

        return ResponseEntity.ok((int) count);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveUser(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserStatusAndNotify(id, UserStatus.APPROVED, userDetails.getUserId());
        recentActivityService.save(new RecentActivity(Activity.APPROVED_USER, "User " + id + " was approved", LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.ok("User approved successfully.");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectUser(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserStatusAndNotify(id, UserStatus.REJECTED, userDetails.getUserId());
        recentActivityService.save(new RecentActivity(Activity.REJECTED_APPROVAL, "User " + id + " was rejected", LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.ok("User rejected successfully.");
    }
}