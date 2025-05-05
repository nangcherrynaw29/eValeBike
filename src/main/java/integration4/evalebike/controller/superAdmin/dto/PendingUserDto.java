package integration4.evalebike.controller.superAdmin.dto;

import integration4.evalebike.domain.User;
import integration4.evalebike.domain.UserStatus;

public record PendingUserDto(
        Integer id,
        String name,
        String email,
        String role,
        UserStatus status
) {
    public static PendingUserDto fromUser(User user) {
        return new PendingUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString(),
                user.getUserStatus()
        );
    }
}