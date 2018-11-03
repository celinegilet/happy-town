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
    public void notifier(String to, String subject, String body) throws NotificationException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new NotificationException(
                    format("Erreur lors de l'envoi du mail : To=%s, Subject=%s, Body=%s", to, subject, body),
                    e);
        }
    }

}
