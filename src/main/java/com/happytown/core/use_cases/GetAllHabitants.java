package com.happytown.core.use_cases;

import com.happytown.core.entities.Habitant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllHabitants {

    private final HabitantProvider habitantProvider;

    public GetAllHabitants(HabitantProvider habitantProvider) {
        this.habitantProvider = habitantProvider;
    }

    public List<Habitant> execute() {
        return habitantProvider.getAll();
    }

}