package com.happytown.service;

import com.happytown.core.entities.Habitant;
import com.happytown.core.use_cases.HabitantProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitantService {

    private final HabitantProvider habitantProvider;

    public HabitantService(HabitantProvider habitantProvider) {
        this.habitantProvider = habitantProvider;
    }

    public List<Habitant> getAll() {
        return habitantProvider.getAll();
    }

}
