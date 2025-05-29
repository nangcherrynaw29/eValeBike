package integration4.evalebike.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordEmail(String toEmail, String rawPassword) {
        try {
            String subject = "Account Created - Your Password";
            String text = """
                    Your account has been successfully created.
                    You will receive an email when your account is activated.
                    Your password is: %s
                    """.formatted(rawPassword);
            sendEmail(toEmail, subject, text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + toEmail, e);
        }
    }

    public void sendPasswordEmailtoBikeOwner(String toEmail, String rawPassword) {
        try {
            String subject = "Account Created - Your Password";
            String text = """
                    Your account has been successfully created.
                    Your password is: %s
                    """.formatted(rawPassword);
            sendEmail(toEmail, subject, text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + toEmail, e);
        }
    }

    public void sendStatusNotificationEmail(String toEmail, String statusMessage) {
        try {
            sendEmail(toEmail, "Account Status Update", statusMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send status notification to " + toEmail, e);
        }
    }

    private void sendEmail(String toEmail, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + toEmail, e);
        }
    }
}