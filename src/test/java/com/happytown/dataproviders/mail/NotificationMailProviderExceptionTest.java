package com.happytown.dataproviders.mail;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.junit.mail.server.test.FakeSmtpRule;
import com.happytown.core.use_cases.NotificationException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableRuleMigrationSupport
@TestPropertySource(properties = {"mail.smtp.host=localhost", "mail.smtp.port=9999"})
class NotificationMailProviderExceptionTest {

    @Autowired
    NotificationMailProvider notificationMailProvider;

    private final int fakePort = 9999 - 1;

    @Rule
    public FakeSmtpRule smtpServer = new FakeSmtpRule(
            ServerConfiguration.create()
                    .bind("localhost")
                    .charset("UTF-8")
                    .port(fakePort)
                    .relayDomains("example.fr"));

    @Test
    void notifier_shouldThrowNotificationException() {
        // Given
        String to = "camille.moulin@example.fr";
        String subject = "Happy Birthday in HappyTown!";
        String body = "Un cadeau va vous être envoyé";

        try {
            // When
            notificationMailProvider.notifier(to, subject, body);
        } catch (NotificationException e) {
            // Then
            assertThat(smtpServer.mailBox()).isEmpty();
            assertThat(e.getMessage()).isEqualTo("Erreur lors de l'envoi du mail : " +
                    "To=camille.moulin@example.fr, Subject=Happy Birthday in HappyTown!, Body=Un cadeau va vous être envoyé");
            assertThat(e.getCause().getMessage()).isEqualTo("Could not connect to SMTP host: localhost, port: 9999");
        }
    }
}