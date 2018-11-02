package com.happytown.dataproviders.file;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.TrancheAge;
import com.happytown.core.entities.TrancheAgeComparator;
import com.happytown.core.use_cases.CadeauxByTrancheAgeException;
import com.happytown.core.use_cases.CadeauxByTrancheAgeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.String.format;

@Component
public class CadeauxByTrancheAgeFileProvider implements CadeauxByTrancheAgeProvider {

    @Value("${file.cadeauxByTrancheAge.path}")
    private String pathFileCadeauxByTrancheAge;

    private static final String DASH = "-";
    private static final String COMMA = ",";

    @Override
    public Map<TrancheAge, List<Cadeau>> get() throws CadeauxByTrancheAgeException {

        TrancheAgeComparator trancheAgeComparator = new TrancheAgeComparator();
        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = new TreeMap<>(trancheAgeComparator);

        try {
            Files.lines(Paths.get(pathFileCadeauxByTrancheAge))
                    .skip(1)
                    .forEach(
                            line -> addLine(line, cadeauxByTrancheAge)
                    );
        } catch (IOException e) {
            throw new CadeauxByTrancheAgeException(
                    format("Erreur lors de la lecture du fichier contenant les cadeaux par tranche d'âge : %s",
                            pathFileCadeauxByTrancheAge),
                    e);
        }
        return cadeauxByTrancheAge;
    }

    private void addLine(String line, Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge) {
        String[] cadeauData = line.split(COMMA);
        String reference = cadeauData[0];
        String description = cadeauData[1];
        BigDecimal montant = new BigDecimal(cadeauData[2]);
        String[] trancheAgeData = cadeauData[3].split(DASH);
        TrancheAge trancheAge = new TrancheAge(Integer.valueOf(trancheAgeData[0]), Integer.valueOf(trancheAgeData[1]));
        if (!cadeauxByTrancheAge.containsKey(trancheAge)) {
            cadeauxByTrancheAge.put(trancheAge, new ArrayList<>());
        }
        Cadeau cadeau = new Cadeau(reference, description, montant, trancheAge);
        cadeauxByTrancheAge.get(trancheAge).add(cadeau);
    }

}
