package org.shedever.testtaskmirea.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }
}
