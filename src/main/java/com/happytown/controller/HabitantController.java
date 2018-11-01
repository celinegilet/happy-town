package com.happytown.controller;

import com.happytown.core.entities.Habitant;
import com.happytown.service.HabitantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habitants")
@Api(value = "API de gestion des habitants de Happy Town")
public class HabitantController {

    private final HabitantService habitantService;

    public HabitantController(HabitantService habitantService) {
        this.habitantService = habitantService;
    }

    @GetMapping
    @ApiOperation("Retourne la liste des habitants de Happy Town")
    public List<Habitant> getAllHabitants() {
        return habitantService.getAll();
    }

}
