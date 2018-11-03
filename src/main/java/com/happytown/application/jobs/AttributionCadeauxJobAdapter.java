package com.happytown.application.jobs;

import com.happytown.domain.use_cases.AttribuerCadeaux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDate;

@Configuration
@EnableScheduling
public class AttributionCadeauxJobAdapter {

    private final AttribuerCadeaux attribuerCadeaux;

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributionCadeauxJobAdapter.class);

    public AttributionCadeauxJobAdapter(AttribuerCadeaux attribuerCadeaux) {
        this.attribuerCadeaux = attribuerCadeaux;
    }

    @Scheduled(cron = "0 0/2 * * * *")
    public void execute() throws IOException {
        LOGGER.info("Start Task execute");
        String fileName = "src/main/resources/cadeaux.txt";
        LocalDate now = LocalDate.now();
        attribuerCadeaux.execute(fileName, now);
        LOGGER.info("End Task execute");
    }
}
