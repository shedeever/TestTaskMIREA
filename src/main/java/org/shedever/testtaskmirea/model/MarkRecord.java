package org.shedever.testtaskmirea.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.entity.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MarkRecord {
    private StudyObject studyObject;
    private Teacher teacher;
    private int term;
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
        return String.format("| %d | %s | %s | %s |",
                term,
                studyObject.getName(),
                teacher.getName(),
                mark.getDescription());
    }
}
