package org.shedever.testtaskmirea.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String name;
    private int countGreat;
    private int countWell;
    private int countGood;
    private int countBad;
    private int countDidntShow;
    private float averange;

    public StudentDto(String name, int countGreat, int countWell, int countGood, int countBad, int countDidntShow, float averange) {
        this.name = name;
        this.countGreat = countGreat;
        this.countWell = countWell;
        this.countGood = countGood;
        this.countBad = countBad;
        this.countDidntShow = countDidntShow;
        this.averange = averange;
    }
}
