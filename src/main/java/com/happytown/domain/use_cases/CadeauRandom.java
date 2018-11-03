package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Cadeau;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CadeauRandom {

    public Cadeau get(List<Cadeau> cadeaux) {
        Random random = new Random();
        return cadeaux.get(random.nextInt(cadeaux.size()));
    }

}
