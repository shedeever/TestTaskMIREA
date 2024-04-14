package org.shedever.testtaskmirea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.shedever.testtaskmirea.model.Mark;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MarkRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "studyObject_id")
    private StudyObject studyObject;
    @ManyToOne
    private Teacher teacher;
    private int term;
    @Enumerated(EnumType.STRING)
    private Mark mark;

    public MarkRecord(StudyObject studyObject, Teacher teacher, int term, Mark mark) throws Exception {
        this.studyObject = studyObject;
        this.teacher = teacher;
        if (studyObject.getCountOfTerms() < term) {
            throw new Exception("Этот предмет состоит из " + studyObject.getCountOfTerms() + " семестров");
        }
        else {
            this.term = term;
        }
        this.mark = mark;
    }

    @Override
    public String toString() {
        return String.format("| %d | %s | %s | %s |",
                term,
                studyObject.getName(),
                teacher.getFullName(),
                mark.getDescription());
    }
}
