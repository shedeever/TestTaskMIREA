package org.shedever.testtaskmirea.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher {
    protected String name;

    public Teacher(String name) {
        this.name = name;
    }
}
