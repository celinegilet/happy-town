package com.happytown.domain.entities;

import java.util.Objects;

public class TrancheAge {

    private Integer ageMin;
    private Integer ageMax;

    public TrancheAge(Integer ageMin, Integer ageMax) {
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrancheAge that = (TrancheAge) o;
        return Objects.equals(ageMin, that.ageMin) &&
                Objects.equals(ageMax, that.ageMax);
    }
}
