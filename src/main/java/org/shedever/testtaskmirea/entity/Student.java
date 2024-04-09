package org.shedever.testtaskmirea.entity;

import lombok.Getter;
import lombok.Setter;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.model.MarkRecord;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Student {
    private String name;
    private int course;
    private List<MarkRecord> gradebook = new ArrayList<>();

    public Student(String name, int course) {
        this.name = name;
        this.course = course;
    }

    public void addMark(StudyObject studyObject, int term, Mark mark) {
        this.gradebook.add(new MarkRecord(this, studyObject, studyObject.getTeacher(), term, mark));
    }

    public float averangeMark(){
        int sum = 0;

        for (MarkRecord mark : gradebook) {
            sum += mark.getMark().getValue();
        }

        return (float) sum / gradebook.size();
    }

    private int getCountByMark(Mark mark) {
        int count = 0;
        for (MarkRecord record : gradebook) {
            if (record.getMark() == mark) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("%s - %d | %d | %d | %d | %d | %.1f |",
                getName(),
                getCountByMark(Mark.Great),
                getCountByMark(Mark.Well),
                getCountByMark(Mark.Good),
                getCountByMark(Mark.Bad),
                getCountByMark(Mark.DidntShow),
                averangeMark());
    }
}
