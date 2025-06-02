package integration4.evalebike.service;

import integration4.evalebike.domain.Role;
import integration4.evalebike.domain.User;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private boolean isValidApproval(User target, User approver) {
        Role targetRole = target.getRole();
        Role approverRole = approver.getRole();

        return switch (targetRole) {
            case TECHNICIAN -> approverRole == Role.ADMIN;
            case ADMIN -> approverRole == Role.SUPER_ADMIN;
            case BIKE_OWNER -> true; // no approval needed, should be auto-approved
            default -> false;
        };
    }

    @Transactional
    public List<User> getUsersByStatus(UserStatus status) {
        return userRepository.findByUserStatus(status);
    }

    @Transactional
    public void updateUserStatusAndNotify(Integer id, UserStatus newStatus, int approverUser) {
        User userToApprove = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        User approver = userRepository.findById(approverUser)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Validate if the approver has permission
        if (!isValidApproval(userToApprove, approver)) {
            throw new AccessDeniedException("You are not authorized to approve this user.");
        }

        userToApprove.setUserStatus(newStatus);
        userRepository.save(userToApprove);

        // Send email notification
        String statusNotification = generateStatusNotification(newStatus);
        emailService.sendStatusNotificationEmail(userToApprove.getEmail(), statusNotification);
    }

    private String generateStatusNotification(UserStatus newStatus) {
        String statusNotification;
        if (newStatus == UserStatus.APPROVED) {
            statusNotification = "Your account has been approved. You can now login.";
        } else {
            statusNotification = "Your account has been rejected. Please contact us for assistance.";
        }
        return statusNotification;
    }
}