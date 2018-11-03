package com.happytown.infrastructure.mail;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.junit.mail.server.test.FakeSmtpRule;
import com.happytown.domain.use_cases.NotificationException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@EnableRuleMigrationSupport
@TestPropertySource(properties = {"mail.smtp.host=localhost", "mail.smtp.port=9999"})
class NotificationMailAdapterExceptionTest {

    @Autowired
    NotificationMailAdapter notificationMailAdapter;

    private final int fakePort = 9999 - 1;

    @Rule
    public FakeSmtpRule smtpServer = new FakeSmtpRule(
            ServerConfiguration.create()
                    .bind("localhost")
                    .charset("UTF-8")
                    .port(fakePort)
                    .relayDomains("example.fr"));

    @Test
    void notifier_shouldThrowNotificationException_whenServeurMailIsDown() {
        // Given
        String to = "camille.moulin@example.fr";
        String subject = "Happy Birthday in HappyTown!";
        String templatePath = "src/main/resources/messageCadeau.txt";
        Map<String, String> templateArgs = new HashMap<>();
        templateArgs.put("prenom", "Camille");
        templateArgs.put("nom", "Moulin");
        templateArgs.put("cadeau", "Peluche souris étoile (Montant : 24.99€ - Référence : a3d832e5)");
        try {
            // When
            notificationMailAdapter.notifier(to, subject, templatePath, templateArgs);
        } catch (NotificationException e) {
            // Then
            assertThat(smtpServer.mailBox()).isEmpty();
            assertThat(e.getMessage()).isEqualTo("Erreur lors de l'envoi du mail : " +
                    "To=camille.moulin@example.fr, Subject=Happy Birthday in HappyTown!, " +
                    "TemplatePath=src/main/resources/messageCadeau.txt, " +
                    "TemplateArgs={cadeau=Peluche souris étoile (Montant : 24.99€ - Référence : a3d832e5), prenom=Camille, nom=Moulin}");
            assertThat(e.getCause().getMessage()).isEqualTo("Could not connect to SMTP host: localhost, port: 9999");
        }
    }

    @Test
    void notifier_shouldThrowNotificationException_whenTemplatePathIsNotValid() {
        // Given
        String to = "camille.moulin@example.fr";
        String subject = "Happy Birthday in HappyTown!";
        String templatePath = "BAD_PATH.txt";
        Map<String, String> templateArgs = new HashMap<>();
        templateArgs.put("prenom", "Camille");
        templateArgs.put("nom", "Moulin");
        templateArgs.put("cadeau", "Peluche souris étoile (Montant : 24.99€ - Référence : a3d832e5)");
        try {
            // When
            notificationMailAdapter.notifier(to, subject, templatePath, templateArgs);
        } catch (NotificationException e) {
            // Then
            assertThat(e.getMessage()).isEqualTo("Erreur lors de l'envoi du mail : " +
                    "To=camille.moulin@example.fr, Subject=Happy Birthday in HappyTown!, " +
                    "TemplatePath=BAD_PATH.txt, " +
                    "TemplateArgs={cadeau=Peluche souris étoile (Montant : 24.99€ - Référence : a3d832e5), prenom=Camille, nom=Moulin}");
            assertThat(e.getCause().toString()).isEqualTo("java.nio.file.NoSuchFileException: BAD_PATH.txt");
        }

    }
}