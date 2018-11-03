package com.happytown.service;

import com.happytown.domain.entities.Habitant;
import com.happytown.repository.HabitantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitantService {

    private final HabitantRepository habitantRepository;

    public HabitantService(HabitantRepository habitantRepository) {
        this.habitantRepository = habitantRepository;
    }

    public List<Habitant> getAll() {
        return habitantRepository.findAll();
    }

}
