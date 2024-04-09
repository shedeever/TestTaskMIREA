package org.shedever.testtaskmirea.entity;

import lombok.Getter;

@Getter
public class MarkRecord {
    private Student student;
    private StudyObject studyObject;
    private Teacher teacher;
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
}
