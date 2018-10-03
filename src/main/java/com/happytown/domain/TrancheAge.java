package com.happytown.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrancheAge {

    @NotNull
    private Integer ageMin;
    @NotNull
    private Integer ageMax;

}
