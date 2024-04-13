package org.shedever.testtaskmirea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.shedever.testtaskmirea.model.Mark;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int course;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<MarkRecord> gradebook;

    public Student(String firstName, String lastName, String patronymic, int course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.course = course;
        this.gradebook = new ArrayList<>();
    }

    public String getFullName(){
        return lastName + " " + firstName + " " + patronymic;
    }

    public void addMark(StudyObject studyObject, int term, Mark mark) {
        try {
            this.gradebook.add(new MarkRecord(studyObject, studyObject.getTeacher(), term, mark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float averangeMark(){
        int sum = 0;

        if (gradebook.isEmpty()){
            return 0;
        }

        for (MarkRecord mark : gradebook) {
            sum += mark.getMark().getValue();
        }

        return (float) sum / gradebook.size();
    }

    public void showGradebook(){
        for (MarkRecord mark : gradebook) {
            System.out.println(mark);
        }
    }

    public int getCountByMark(Mark mark) {
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
                getFullName(),
                getCountByMark(Mark.Great),
                getCountByMark(Mark.Well),
                getCountByMark(Mark.Good),
                getCountByMark(Mark.Bad),
                getCountByMark(Mark.DidntShow),
                averangeMark());
    }
}
