package com.happytown.controller;

import com.happytown.service.HappyTownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/attributionCadeaux")
@Api(value = "API permettant d'attribuer un cadeau aléatoire aux habitants de Happy Town arrivés depuis plus de 1 an")
public class HappyTownController {

    private final HappyTownService happyTownService;

    private static final String SMTP_HOST = "localhost";
    private static final int SMTP_PORT = 2525;

    public HappyTownController(HappyTownService happyTownService) {
        this.happyTownService = happyTownService;
    }

    @PostMapping
    @ApiOperation("Permet d'attribuer un cadeau aléatoire aux habitants de Happy Town arrivés depuis plus de 1 an")
    public void attribuerCadeaux() throws IOException, MessagingException {
        String fileName = "src/main/resources/cadeaux.txt";
        LocalDate now = LocalDate.now();
        happyTownService.attribuerCadeaux(fileName, now, SMTP_HOST, SMTP_PORT);
    }

}
