package org.shedever.testtaskmirea.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyObject {
    private String name;
    private int countOfTerms;
    private Teacher teacher;

    public StudyObject(String name, Teacher teacher, int countOfTerms) {
        this.name = name;
        this.countOfTerms = countOfTerms;
        this.teacher = teacher;
    }
}
