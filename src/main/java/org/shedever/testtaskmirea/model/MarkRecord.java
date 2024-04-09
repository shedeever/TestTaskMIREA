package org.shedever.testtaskmirea.model;

import lombok.Getter;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.entity.Teacher;

@Getter
public class MarkRecord {
    private final Student student;
    private final StudyObject studyObject;
    private final Teacher teacher;
    private int term;
    private Mark mark;

    public MarkRecord(Student student, StudyObject studyObject, Teacher teacher, int term, Mark mark) {
        this.student = student;
        this.studyObject = studyObject;
        this.teacher = teacher;
        if (studyObject.getCountOfTerms() < term) {
            System.out.println("Этот предмет состоит из " + studyObject.getCountOfTerms() + " семестров");
            return;
        }
        else {
            this.term = term;
        }
        this.mark = mark;
    }

    @Override
    public String toString() {
        return String.format("| %s | %d | %s | %s | %s |",
                student.getName(),
                term,
                studyObject.getName(),
                teacher.getName(),
                mark.getStrvalue());
    }
}
