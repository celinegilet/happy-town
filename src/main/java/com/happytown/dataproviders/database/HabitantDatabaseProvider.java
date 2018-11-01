package com.happytown.dataproviders.database;

import com.happytown.core.entities.Habitant;
import com.happytown.core.use_cases.HabitantProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class HabitantDatabaseProvider implements HabitantProvider {

    private final HabitantJpaRepository habitantJpaRepository;

    public HabitantDatabaseProvider(HabitantJpaRepository habitantJpaRepository) {
        this.habitantJpaRepository = habitantJpaRepository;
    }

    @Override
    public List<Habitant> getAll() {
        return habitantJpaRepository.findAll()
                .stream()
                .map(this::toHabitant)
                .collect(toList());
    }

    @Override
    public List<Habitant> getEligiblesCadeaux(LocalDate dateArriveeCommune) {
        return habitantJpaRepository.findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(dateArriveeCommune)
                .stream()
                .map(this::toHabitant)
                .collect(toList());
    }

    @Override
    public void save(Habitant habitant) {
        habitantJpaRepository.save(toHabitantJpa(habitant));
    }

    private Habitant toHabitant(HabitantJpa habitantJpa) {
        Habitant habitant = new Habitant();
        habitant.setId(habitantJpa.getId());
        habitant.setNom(habitantJpa.getNom());
        habitant.setPrenom(habitantJpa.getPrenom());
        habitant.setEmail(habitantJpa.getEmail());
        habitant.setDateNaissance(habitantJpa.getDateNaissance());
        habitant.setDateArriveeCommune(habitantJpa.getDateArriveeCommune());
        habitant.setAdressePostale(habitantJpa.getAdressePostale());
        habitant.setCadeauOffert(habitantJpa.getCadeauOffert());
        habitant.setDateAttributionCadeau(habitantJpa.getDateAttributionCadeau());
        return habitant;
    }

    private HabitantJpa toHabitantJpa(Habitant habitant) {
        HabitantJpa habitantJpa = new HabitantJpa();
        habitantJpa.setId(habitant.getId());
        habitantJpa.setNom(habitant.getNom());
        habitantJpa.setPrenom(habitant.getPrenom());
        habitantJpa.setEmail(habitant.getEmail());
        habitantJpa.setDateNaissance(habitant.getDateNaissance());
        habitantJpa.setDateArriveeCommune(habitant.getDateArriveeCommune());
        habitantJpa.setAdressePostale(habitant.getAdressePostale());
        habitantJpa.setCadeauOffert(habitant.getCadeauOffert());
        habitantJpa.setDateAttributionCadeau(habitant.getDateAttributionCadeau());
        return habitantJpa;
    }
}
