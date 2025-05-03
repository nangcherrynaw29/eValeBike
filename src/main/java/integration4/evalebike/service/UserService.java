package integration4.evalebike.service;

import integration4.evalebike.domain.User;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.repository.UserRepository;
import integration4.evalebike.utility.PasswordUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordUtility passwordUtility;

    public UserService(UserRepository userRepository, PasswordUtility passwordUtility) {
        this.userRepository = userRepository;
        this.passwordUtility = passwordUtility;
    }

    @Transactional
    public List<User> getUsersByStatus(UserStatus status) {
        return userRepository.findByUserStatus(status);
    }

    @Transactional
    public void updateUserStatusAndNotify(Integer id, UserStatus newStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        user.setUserStatus(newStatus);
        userRepository.save(user);

        // Send email notification
        String statusNotification = generateStatusNotification(newStatus);
        passwordUtility.sendStatusNotificationEmail(user.getEmail(), statusNotification);
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