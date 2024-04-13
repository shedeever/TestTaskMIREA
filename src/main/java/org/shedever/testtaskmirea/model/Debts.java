package org.shedever.testtaskmirea.model;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Debts {
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
}
