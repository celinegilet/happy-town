package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Habitant;

import java.util.List;

public class GetAllHabitants {

    private final HabitantPort habitantPort;

    public GetAllHabitants(HabitantPort habitantPort) {
        this.habitantPort = habitantPort;
    }

    public List<Habitant> execute() {
        return habitantPort.getAll();
    }

}
