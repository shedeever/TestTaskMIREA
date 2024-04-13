package org.shedever.testtaskmirea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "StudyObject")
public class StudyObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int countOfTerms;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public StudyObject(String name, Teacher teacher, int countOfTerms) {
        this.name = name;
        this.countOfTerms = countOfTerms;
        this.teacher = teacher;
    }
}
