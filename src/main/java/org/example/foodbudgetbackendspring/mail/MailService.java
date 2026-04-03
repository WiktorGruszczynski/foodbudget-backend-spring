package org.example.foodbudgetbackendspring.mail;

import jakarta.annotation.Nonnull;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendEmail(@Nonnull String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(mimeMessage);
    }

    public void sendRegistrationEmail(@Nonnull String to, @Nonnull String verificationCode) {
        try {
            Context context = new Context();
            context.setVariable("verification_code", verificationCode);

            sendEmail(
                    to,
                    "Verification code",
                    templateEngine.process("registration-email", context)
            );

        }
        catch (Exception e) {
            throw new RuntimeException("Could not send password reset email: " + e.getMessage());
        }
    }

    public void sendPasswordResetEmail(@Nonnull String to, @Nonnull String verificationCode){
        try {
            Context context = new Context();
            context.setVariable("verification_code", verificationCode);

            sendEmail(
                    to,
                    "Password reset",
                    templateEngine.process("password-reset-email", context)
            );
        } catch (Exception e) {
            throw new RuntimeException("Could not send password reset email: " + e.getMessage());
        }
    }
}
