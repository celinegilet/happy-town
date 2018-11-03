package com.happytown.infrastructure.mail;

import com.happytown.domain.use_cases.NotificationException;
import com.happytown.domain.use_cases.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;

@Component
public class NotificationMailAdapter implements NotificationPort {

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private Integer smtpPort;

    @Value("${mail.smtp.from}")
    private String from;

    @Override
    public void notifier(String to, String subject, String templatePath, Map<String, String> templateArgs) throws NotificationException {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            Session session = Session.getInstance(props, null);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            String body = new String(Files.readAllBytes(Paths.get(templatePath)));
            for (Map.Entry<String, String> arg : templateArgs.entrySet()) {
                body = body.replaceAll("#" + arg.getKey(), arg.getValue());
            }
            msg.setText(body);

            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            throw new NotificationException(
                    format("Erreur lors de l'envoi du mail : To=%s, Subject=%s, TemplatePath=%s, TemplateArgs=%s", to, subject, templatePath, templateArgs),
                    e);
        }
    }

}
