package integration4.evalebike.utility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordUtility {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public PasswordUtility(BCryptPasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public String generateRandomPassword(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void sendPasswordEmail(String toEmail, String rawPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Account Created - Your Password");
            message.setText("Your account has been successfully created." +
                    "You will receive an email when your account is activated. " +
                    "Your password is: " + rawPassword);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + toEmail, e);
        }
    }

    public void sendStatusNotificationEmail(String toEmail, String statusMessage) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Account Status Update");
            message.setText(statusMessage);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send status notification to " + toEmail, e);
        }
    }
}