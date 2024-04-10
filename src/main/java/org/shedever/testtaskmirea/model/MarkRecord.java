package org.shedever.testtaskmirea.model;

import lombok.Getter;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.entity.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MarkRecord {
    private final Student student;
    private final StudyObject studyObject;
    private final Teacher teacher;
    private final int term;
    private final Mark mark;

    public MarkRecord(Student student, StudyObject studyObject, Teacher teacher, int term, Mark mark) throws Exception {
        this.student = student;
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

    public static Map<StudyObject, Integer> countDebts(List<Student> students){
        Map<StudyObject, Integer> debts = new HashMap<>();

        for(Student student : students){
            for (MarkRecord markRecord : student.getGradebook()){
                if (markRecord.getMark() == Mark.DidntShow){
                    if (!debts.containsKey(markRecord.getStudyObject())){
                        debts.put(markRecord.getStudyObject(), 1);
                    }
                    else {
                        debts.put(markRecord.getStudyObject(), debts.get(markRecord.getStudyObject()) + 1);
                    }
                }
            }
        }

        return debts;
    }

    public static List<Map.Entry<StudyObject, Integer>> sortDebtsList(Map<StudyObject, Integer> debts) {
        List<Map.Entry<StudyObject, Integer>> debtsList = new ArrayList<>(debts.entrySet());
        debtsList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return debtsList;
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
