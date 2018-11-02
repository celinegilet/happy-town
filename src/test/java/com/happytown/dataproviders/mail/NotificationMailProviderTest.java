package com.happytown.dataproviders.mail;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.fakesmtp.model.EmailModel;
import com.github.sleroy.junit.mail.server.test.FakeSmtpRule;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.mail.internet.MimeUtility.decode;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableRuleMigrationSupport
@TestPropertySource(properties = {"mail.smtp.host=localhost", "mail.smtp.port=9999"})
class NotificationMailProviderTest {

    @Autowired
    NotificationMailProvider notificationMailProvider;

    @Rule
    public FakeSmtpRule smtpServer = new FakeSmtpRule(
            ServerConfiguration.create()
                    .bind("localhost")
                    .charset("UTF-8")
                    .port(9999)
                    .relayDomains("example.fr"));

    @Test
    void notifier_verifyMail_whenServerIsUp() throws IOException, MessagingException {
        // Given
        String to = "camille.moulin@example.fr";
        String subject = "Happy Birthday in HappyTown!";
        String body = "Un cadeau va vous être envoyé";

        // When
        notificationMailProvider.notifier(to, subject, body);

        // Then
        EmailModel email = smtpServer.mailBox().get(0);
        ByteArrayInputStream mailInputStream = new ByteArrayInputStream(email.getEmailStr().getBytes(UTF_8));
        String emailStr = IOUtils.toString(decode(mailInputStream, "quoted-printable"), UTF_8);

        assertThat(smtpServer.mailBox()).hasSize(1);
        assertThat(email.getFrom()).isEqualTo("mairie@happytown.com");
        assertThat(email.getTo()).isEqualTo(to);
        assertThat(email.getSubject()).isEqualTo(subject);
        assertThat(emailStr).contains(body);
    }
}