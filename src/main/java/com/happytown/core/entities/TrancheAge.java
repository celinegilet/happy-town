package com.happytown.core.entities;

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
}
