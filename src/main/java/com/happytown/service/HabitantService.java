package com.happytown.service;

import com.happytown.domain.entities.Habitant;
import com.happytown.domain.use_cases.HabitantPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitantService {

    private final HabitantPort habitantPort;

    public HabitantService(HabitantPort habitantPort) {
        this.habitantPort = habitantPort;
    }

    public List<Habitant> getAll() {
        return habitantPort.getAll();
    }

}
